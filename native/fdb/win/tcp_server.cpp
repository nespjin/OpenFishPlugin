//
// Created by jin on 2022/4/12.
//
#include <cstdio>
#include <winsock2.h>
#include <pthread.h>
#include "../tcp_server.h"
#include "../tcp_common.h"

//#pragma comment (lib, "ws2_32.lib")  //load ws2_32.dll // using cmake

static const char *TAG = "tcp_server";

static SOCKET listenSocket = INVALID_SOCKET;
static SOCKET clientSocket = INVALID_SOCKET;

bool isStop = false;

int tcp_server_init(int port) {
    isStop = false;
    int ret;

    WSADATA wsaData;
    ret = WSAStartup(MAKEWORD(2, 2), &wsaData);
    if (ret != NO_ERROR) {
        printf("%s: WSAStartup failed with error: %d\n", TAG, ret);
        return 1;
    }

    listenSocket = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP);

    if (listenSocket == INVALID_SOCKET) {
        printf("%s: Error at socket(): %ld\n", TAG, WSAGetLastError());
        tcp_server_stop();
        return 1;
    }

    SOCKADDR_IN service;
    memset(&service, 0, sizeof(service));

    service.sin_family = PF_INET;
//    service.sin_addr.s_addr = htonl(INADDR_ANY);
    service.sin_addr.s_addr = inet_addr("127.0.0.1");
    service.sin_port = htons(port);

    printf("%s: server init at addr=%s, port=%d\n", TAG, inet_ntoa(service.sin_addr), port);

    ret = bind(listenSocket, (SOCKADDR *) &service, sizeof(service));
    if (ret == SOCKET_ERROR) {
        printf("%s: bind function failed with error %d\n", TAG, WSAGetLastError());
        tcp_server_stop();
        return 1;
    }
    return 0;
}

void *tcp_server_handleClientThreadRunnable(void *arg) {
    printf("%s: handleClientThreadRunnable start tid = %d\n", TAG, pthread_self());
    if (clientSocket == INVALID_SOCKET) {
        printf("%s: accept failed with error: %d\n", TAG, WSAGetLastError());
        tcp_server_stop();
        return nullptr;
    }

    auto *args = (HandleClientThreadArgs *) arg;

    printf("%s: accepted connection from %s\n", TAG, args->addr);

    int recvBufLen = TCP_BUFFER_SIZE;
    char recvBuf[TCP_BUFFER_SIZE];
    int recvLen;
    while (true) {
        recvLen = recv(clientSocket, recvBuf, recvBufLen, 0);
        if (recvLen == 0) {
            printf("%s: connection closed\n", TAG);
            break;
        } else if (recvLen == SOCKET_ERROR) {
            printf("%s: recv failed with error: %d\n", TAG, WSAGetLastError());
            break;
        } else {
            char *outbuf = new char[recvLen + 1];
            memcpy(outbuf, recvBuf, recvLen);
            outbuf[recvLen] = 0;
            args->onReceiveListener(outbuf, recvLen);
            delete[] outbuf;
        }
    }
}

int tcp_server_run(OnReceiveListener onReceiveListener) {
    if (listenSocket == 0) {
        printf("%s: listen socket is null\n", TAG);
        return 1;
    }

    if (listen(listenSocket, SOMAXCONN) == SOCKET_ERROR) {
        printf("%s: listen listenSocket failed with error: %d\n", TAG, WSAGetLastError());
    }

    printf("%s: listening on socket...\n", TAG);

    SOCKADDR_IN clientAddr;
    int addrLen = sizeof(clientAddr);

    auto *handleClientThreadArgs = new HandleClientThreadArgs{
            .addr = inet_ntoa(clientAddr.sin_addr),
            .onReceiveListener = onReceiveListener
    };

    while (!isStop) {
        printf("%s: please waiting to connect...\n", TAG);
        clientSocket = accept(listenSocket, (SOCKADDR *) &clientAddr, &addrLen);
        pthread_t tid;
        printf("%s: main thread id: %d\n", TAG, (int) pthread_self());
        int ret1 = pthread_create(&tid, nullptr, tcp_server_handleClientThreadRunnable, handleClientThreadArgs);
        if (ret1) {
            printf("%s: pthread_create failed with error: %d\n", TAG, ret1);
        }
    }

    delete handleClientThreadArgs;

    return 0;
}

int tcp_server_send(const char *data, int len) {
    send(clientSocket, data, len, 0);
    return 0;
}

int tcp_server_stop() {
    isStop = true;
    int ret = 0;
    if (listenSocket != INVALID_SOCKET) {
        if (closesocket(listenSocket) == SOCKET_ERROR) {
            printf("%s: close listenSocket failed with error %d\n", TAG, WSAGetLastError());
            ret = 1;
        }
    }

    if (clientSocket != INVALID_SOCKET) {
        if (closesocket(clientSocket) == SOCKET_ERROR) {
            printf("%s: close clientSocket failed with error %d\n", TAG, WSAGetLastError());
            ret = 1;
        }
    }

    WSACleanup();
    return ret;
}

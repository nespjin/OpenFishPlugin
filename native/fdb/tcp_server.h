//
// Created by jin on 2022/4/12.
//

#ifndef FDB_TCP_SERVER_H
#define FDB_TCP_SERVER_H

#include "tcp_common.h"

typedef struct HandleClientThreadArgs {
    char *addr;
    TcpServer &server;

    OnReceiveListener onReceiveListener;
} HandleClientThreadArgs;

/**
 * init the server
 *
 * @param port the port of the server
 * @return 0 if success, -1 if failed
 */
int tcp_server_init(TcpServer &server, InitConfig initConfig);

/**
 * run the server
 *
 * @return 0 if success, -1 if failed
 */
int tcp_server_run(TcpServer &server, OnReceiveListener onReceiveListener);

int tcp_server_send(TcpServer &server, const char *data, int len);

/**
 * stop the server
 *
 * @return 0 if success, -1 if failed
 */
int tcp_server_stop(TcpServer &server);

#endif //FDB_TCP_SERVER_H

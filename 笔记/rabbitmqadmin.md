如果我们创建 RabbitMQ 容器的时候使用的是 rabbitmq:3-management 镜像，那么默认情况下，rabbitmqadmin 就是安装好的。

### rabbitmqadmin 的功能
- 列出 exchanges, queues, bindings, vhosts, users, permissions, connections and channels。
- 创建和删除 exchanges, queues, bindings, vhosts, users and permissions。
- 发布和获取消息，以及消息详情。
- 关闭连接和清空队列。
- 导入导出配置。

命令所在位置：/usr/local/bin/rabbitmqadmin

```shell

# 查看所有交换机：
rabbitmqadmin list exchanges

#查看所有队列：

rabbitmqadmin list queues

# 查看所有 Binding：
rabbitmqadmin list bindings

# 查看所有虚拟主机

rabbitmqadmin list vhosts

# 查看所有用户信息：
rabbitmqadmin list users

# 查看所有权限信息：
rabbitmqadmin list permissions

# 查看所有连接信息：

rabbitmqadmin list connections

# 查看所有通道信息：

rabbitmqadmin list channels
```

举例：

```shell

# 首先创建一个名为 javaboy-exchange 的交换机：
rabbitmqadmin declare exchange name=javaboy-exchange durable=true auto_delete=false type=direct

# 接下来创建一个名为 javaboy-queue 的队列：
rabbitmqadmin declare queue name=javaboy-queue durable=true auto_delete=false

# 接下来再创建一个 Binding，将交换机和消息队列绑定起来：

#source：源，其实就是指交换机。
#destination：目标，其实就是指消息队列。
#routing_key：这个就是路由的 key。

rabbitmqadmin declare binding source=javaboy-exchange destination=javaboy-queue routing_key=javaboy-routing

# 接下来发布一条消息：
rabbitmqadmin publish routing_key=javaboy-queue payload="hello javaboy"

# 查看队列中的消息（只查看，不消费，看完之后消息还在）：

rabbitmqadmin get queue=javaboy-queue

# 清空一个队列中的消息：
rabbitmqadmin purge queue name=javaboy-queue
```

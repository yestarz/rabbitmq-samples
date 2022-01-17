RabbitMQ 相当于一个 Excel 文件，而 vhost 则是 Excel 文件中的一个个 sheet，我们所有的操作都是在某一个 sheet 上进行操作。

### 命令行创建 vhost

```shell
docker exec -it rabbit01 /bin/bash

# 查看vhost列表

rabbitmqctl list_vhosts 

# 执行如下命令创建一个名为 /myvh 的 vhost
rabbitmqctl add_vhost myvh

# 删除，删了以后，这个vhost里面的交换机、用户、队列全部会被删除
rabbitmqctl delete_vhost myvh

# 设置lisi绑定到myvh这个vhost里面  ，几个*表示的意思：
# 1.这个用户在所有的资源上都拥有可配置的权限（比如可以创建消息队列、交换机等）
# 2. 在所有资源上都有写权限（比如发消息的权限）
# 3. 在所有资源上有读权限（比如消费消息）
rabbitmqctl set_permissions -p myvh lisi ".*" ".*" ".*"

# 移除lisi在myvh上的权限
rabbitmqctl clear_permissions -p myvh lisi
```

### 命令行添加用户

```sh

## 添加用户 用户名 javaboy 密码123

rabbitmqctl add_user javaboy 123

# 将 javaboy 的密码改为 123456
rabbitmqctl change_password javaboy 123456

# 通过如下命令可以验证用户密码

rabbitmqctl authenticate_user javaboy 123456

# 查看所有的用户
rabbitmqctl list_users 

# 给javaboy设置administrator的角色
rabbitmqctl set_user_tags javaboy administrator

# 删除javaboy
rabbitmqctl delete_user javaboy
```

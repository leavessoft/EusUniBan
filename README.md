# EusUniBan

A decentralized ban-list sharing plugin for Minecraft server.

![ScreenShot.png](https://raw.githubusercontent.com/leavessoft/EusUniBan/master/ScreenShot.png)
![Description_EN.png](https://raw.githubusercontent.com/leavessoft/EusUniBan/master/Description_EN.png)

## Features

* Sharing your ban-list without a central server
* Benefiting from other servers without changing your own local ban-list
* Subscribing servers you trust
* Encrypting your shared ban-list with customizable password
* Access control:
  * Server whitelist
  * Server blacklist
  * Request frequency controlling



## Extra Requirement

* An open TCP port that is accessible by public



## Subscription

*config.yml -> Subscription*

```yaml
Subscription:
  0: #ID of the server, must be unique
    Host: 'example.com' #Host name or IP
    Port: 60009 #Port of UniBan Broadcast
    Password: 'UniBan' #You may ask for the password from the server owner
```



## Coming Soon

* The ability for operators to handle emergency situations about broadcast server
* Working with third-party ban managing plugins
* Fully Bungeecord Supported
* Server Identifier
* Reusing the port of Minecraft server
* Ban-sharing status display which can be accessed in browsers



## Change Log

### 1.0 Snapshot3

* Add:
  * Threshold settings for warning and preventing when a player banned online entering
  * Update checker
* Fix:
  * Error when config was deleted before reloading
  * SubscriptionRefreshPeriod will not be loaded when broadcast is disabled
  * Tab complete still work even if a player does not have permission "uniban.admin"
  * The player would not be removed even if he/she is unbanned from all subscribed servers

### 1.0 Snapshot2

* Partly support for Bungeecord
* Fixed:
  * Hostname duplication in ban-list cache
  * Wrong hostname displayed in ban-list cache

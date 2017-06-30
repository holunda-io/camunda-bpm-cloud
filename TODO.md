Too lazy to write issues ....

* documentation: new archtecture graphics
* zuul: dynamic routing for engines based on prefixed taskId
  * engine/default/task/complete/simple-12345 -> simple/engine/default/task/complete/simple-12345
* zuul: dynamic registration of process engines based on eureka meta
* complete with command/event
* replay based on shared eventstore (mysql)
* (use cloudmqp rabbit node)
* camunda rest-like task api for task query (camunda tasklist as client)

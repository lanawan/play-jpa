# play-jpa

Этот простейшее приложение для работы с Postgres используя JPA в рамках Play framework на Java.
<br> При доработке может быть использовано в качестве шаблона.

Реализованы :
* из CRUD только create и read
* асинхронные операции
* форма в представлении цепляющая bean для операции create

Для работы необходимо установить Postgres сделать новую базу play в схеме public
	

Для подключения базы необходимо изменить datasource в файле /conf/application.conf следующее :
* default.url = "jdbc:postgresql://localhost:5432/play"
* default.username = postgres
* default.password = "admin"
* fixedConnectionPool (опционально)

Также нужно обратить внимание на файл конфигурации jpa : /conf/META-INF/persistence.xml
Опционально в него можно ввести иные параметры.

Также обратить внимание на обязательные зависимости в build.sbt :
* libraryDependencies += javaJpa
* libraryDependencies += "org.postgresql" % "postgresql" % "42.2.12"
* libraryDependencies += "org.hibernate" % "hibernate-core" % "5.4.14.Final"
Опционально изменить версии подключаемых библиотек.

Этот вебинар на youtube в 3-х частях :
<a href="https://youtu.be/Kdmuq4JysMA" target="_blank">Часть 1</a>
<a href="https://youtu.be/95fytTToHA4" target="_blank">Часть 2</a>
<a href="https://youtu.be/eVM7cpDvxhA" target="_blank">Часть 3</a>

Детальная документация :

* https://www.playframework.com/documentation/latest/JavaJPA
* https://www.playframework.com/documentation/latest/JavaAsync

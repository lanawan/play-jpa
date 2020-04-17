# play-jpa

Этот простейшее приложение для работы с Postgres используя JPA в рамках Play framework на Java.
<br> При доработке может быть использовано в качестве шаблона.

Реализованы :
* из CRUD только create и read
* асинхронные операции
* форма в представлении цепляющая bean для операции create

Для работы необходимо установить Postgres сделать новую базу play и сделать следующую таблицу в схеме public :

	CREATE TABLE public.student
	(
		id integer NOT NULL,
		firstname character varying(50) COLLATE pg_catalog."default",
		lastname character varying(50) COLLATE pg_catalog."default",
		age integer,
		CONSTRAINT student_pkey PRIMARY KEY (id)
	)
	

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



Детальная документация :

* https://www.playframework.com/documentation/latest/JavaJPA
* https://www.playframework.com/documentation/latest/JavaAsync

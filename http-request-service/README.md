Пам'ятка
======

До модулів типу interview-sheduller, group-manager, applicant-manager і т.д. слід у інших сервісах звертатись через http-request-service. Чому? Бо вони компілюються в WAR'ник і в теорії навіть можуть лежати на іншій машинці. Просто закинути на них dependency у власному POM'і і ходити по сервісах? За таке сажати треба!1 (або відправляти на 2 тижні робити WADL ))

З ними треба м'яко, лагідно, делікатно.
Як?

__1__. додаєте в свій POM maven залежність:

````xml
<dependency>
  <groupId>com.softserveinc.ita</groupId>
  <artifactId>http-request-service</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
````

__2__. творите bean в своєму контексті, обов'язково дописавши конструктор, в параметрах якого базова URL'ка сервіса, який вам потрібен:

````xml
<bean id="httpRequestExecutor" class="com.softserveinc.ita.service.impl.HttpRequestExecutorRestImpl">
  <constructor-arg name="baseUrl" value="http://localhost:8080"/>
</bean>
````

__3__. В'яжете десь у своєму класі:

````java
@Autowired
    private HttpRequestExecutor httpRequestExecutor;
````

__4__. використовуєте:

````java
try {
                              //id потрібної сутності
                                         |       //її клас
    httpRequestExecutor.getObjectByID(classId, Class.class);
    ...
}
catch (HttpRequestException ex) {
    ... //класна фіча: виняткова ситуація виникає лише при неОК (!200).
}
````

Конвенція!1
======

Якщо у вас контроллер сервісу написаний по конвенції, то ви класний пацан (тілка), а ні - то не класний.
і ці методи зможуть юзати інші сервіси. yey)

---
{entity} - назва сутності e.g. applicant

{base url} - базова адреса, типу localhost:8080, etc.

{entityA},{entityB},{entityC} - варіації сутностей

````
{base url}/{entity}s/{id}  -  returns об`єкт типу {entity}
get{entity}by{entity}id    -  {entity}

EXAMPLE: http://176.36.11.25:8080/applicants/eba684a0-0dc3-11e4-9191-0800200c9a66

метод який треба юзати в http-request-service:
<T> T getObjectByID(String id, Class<T> objectClass)
````
````
{base url}/{entity}s  -  returns список всіх IDшників об`єктів типу {entity}
getAll{entity}sId     -  List<String>, where String is {entity} id

EXAMPLE: http://176.36.11.25:8080/applicants/

метод який треба юзати в http-request-service:
List<String> getAllObjectsID(Class objectClass)
````
````
{base url}/{entity}s?{entityA}={valueA}&{entityB}={valueB}  -  returns список IDшників об`єктів типу {entity} по {entityA} id та по {entityB} id. При бажанні можна хоч сто параметрів добавити)
get{entity}sIdBy{entityA}IdAnd{entityB}Id                   -  List<String>, where String is {entity} id

EXAMPLE: http://176.36.11.25:8080/applicants?cat=d8967e63-0dc4-11e4-9191-0800200c9a66&dog=d8967e63-0dc4-11e4-9191-0800200c9a66

метод який треба юзати в http-request-service:
List<String> getListObjectsIdByPrams(Class objectClass, Map<Class, String> urlValues)
````
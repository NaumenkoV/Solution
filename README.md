ITA JAVA )
---c
since May 29


Пам'ятка no2:
--
Це НЕ наш WAR'ник, і ми його не доїмо!!1

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

успіхів!

Пам'ятка no1:
--
потрібен тільки в рамках фічі, бо review'еру НЕ ЦІКАВО слідкувати за процесом польоту фантазії, проб, помилок, тощо. Тільки кінцевий результат.*

In code we trust!

<a href="http://www.youtube.com/watch?feature=player_embedded&v=Eq3CuMDXaPs
" target="_blank"><img src="http://img.youtube.com/vi/Eq3CuMDXaPs/0.jpg" 
alt="AND SO YOU CODE" width="240" height="180" border="10" /></a>


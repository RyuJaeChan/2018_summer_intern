JAVA
====
  * [Class.forName](#classforname)
  * [StaticBlock](#staticblock)
  * [JavaReflection 아직 안슴](#javareflection)
  * [eclipse 플러그인 설치](#eclipse-플러그인-설치)
  * [Mock Object란 무엇인가](#mock-object란-무엇인가)
  * [Annotation 더공부ㅐㅎ야함](#annotation)
  * [스프링 proxy 아직작성안함](#스프링-proxy)
  * [AutoCloseable](#autocloseable)
  * [<>제네릭](#<>-제네릭)
  * [jdbc management](#jdbc-management)
  * [이클립스 단축키](#이클립스-단축키들)
  * [접근제한자](#접근제한자)
  * [자바 유틸 패키지 경로](#자바-유틸-패키지-경로)
  * [native 키워드](#native-키워드)

{:toc}


Class.forName
--------------------
JDBC 드라이버를 로드하는 코드를 보면 Class.forName을 사용하는 것을 볼 수 있다. 이 Class.forName의 의미는 무엇일까?
```java
Class.forName("com.mysql.jdbc.Driver");
Connection conn = DriverManager.getConnection();
```
코드를 살펴보면 리턴값을 받지도 않고 ```DriverManager```의 ```getConnection```메서드를 바로 사용할 수 있다. 또 ```forName```의 인자로 패키지의 경로가 설정된 것을 볼 수 있다. 왜 이렇게 구현한 것일까?
각 JDBC마다 드라이버와 Connection의 구현은 다를 것이다. 각 DBMS마다 다른 API를 사용하여 DB에 연결할 경우
DBMS를 변경할 일이 생기면 복잡한 작업이 될 것이다.
Java에서는 Connection, Driver 인터페이스와 Driver를 등록하고 그 드라이버의 Connection을 리턴해주는 DriverManager 클래스를 제공한다. 각 벤더에서는 Driver와 Conneciton을 구현하고 DriverManager라는 클래스를 통해 사용자가 이들을 사용 하도록한다.
```java
package serviceprovider;
public interface Connection {

}

// package serviceprovider;
public interface Driver {
	Connection getConnection();
}
```
java는 위와 같은 두개의 인터페이스를 제공한다. 각 벤더는 이 인터페이스를 상속하여 드라이버와 커넥션을 구연한다.

```java
//DriverManager 예시
package serviceprovider;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class DriverManager {
	private DriverManager() {}
	private static final Map<String,Driver> drivers = new ConcurrentHashMap<String,Driver>();
	public static final String DEFAULT_DRIVER_NAME = "default";
	public static void registerDefaultPrivider(Driver d) {
		System.out.println("Driver 등록");
		registerDriver(DEFAULT_DRIVER_NAME, d);
	}
	public static void registerDriver(String name, Driver d) {
		drivers.put(name,d);
	}
	public static Connection getConnection() {
		return getConnection(DEFAULT_DRIVER_NAME);
	}
	public static Connection getConnection(String name) {
		Driver d = drivers.get(name);
		if(d==null) throw new IllegalArgumentException();
		return d.getConnection();
	}
}
```
```DriverManger```는 드라이버를 등록하고 관리하는 기능을 제공한다. 우리가 처음에 ```DriverManager.getConnection```을 호출하면 여기에 저장이 된다. static으로 변수와 메소드가 선언되어 있기 때문에 객체 생성없이 바로 사용할 수 있다.

```java
	//OracleDriver 클래스의 일부분
	static {
    	defaultDriver = null; Timestamp timestamp = Timestamp.valueOf("2000-01-01 00:00:00.0");
    	try {
	    	if(defaultDriver == null) {
    			defaultDriver = new OracleDriver();
    			DriverManager.registerDriver(defaultDriver);
    		}
    	} catch(RuntimeException runtimeexception) {

    	} catch(SQLException sqlexception) {

	    }
    }
```
Class.forName(String name) 클래스에 의해 클래스가 로드 될 때 static 필드의 내용이 실행된다. <- 그렇다고 합니다.
이를 이용해 class 내부에 static 필드를 통해 자기 자신을 DriverManager 클래스에 등록한다.
Java에서는 어떤 DB든 동일하게 사용할 수 있게 Connection과 DriverManger 등을 인터페이스로 제공하여 각 벤더가 이를 구현하게 만들었다. 이렇게 동일한 인터페이스로 구현하여 Class.forName을 통해 동적으로 객체를 가져와 사용할 수 있다. 결국 어떤 DBMS를 사용하든 DB 사용의 구현은 동일하게 할 수 있다.



StaticBlock
------------------------------------------------------------------
jdbc 구현 방법을 살펴보다가 클래스 정의 부분에 static 블럭이 있는 것을 봤다. 처음 보는 문법이라서 신기했다. 이런 블록을 초기화 블럭(initialization block)이라고 한다.
```java
class Foo{
    static {
        /* 클래스 초기화 블럭 */
    }

    {   /* 인스턴스 초기화 블럭 */ }
}
```
클래스 초기화 블럭은 클래스 변수의 복잡한 초기화에 사요오딘다. 클래스가 처음 로딩될 때 한번만 수행된다. 인스턴스 초기화 블럭은 인스턴스가 생성될 때 마다 수행된다. 이는 생성자보다 먼저 수행된다.
인스턴스 변수의 초기화는 주로 생성자를 사용하기 때문에 인스턴스 초기화 블럭은 잘 사용되지 않는다.
대신 클래스의 모든 생성자에서 공통적으로 수행되어져야 하는 코드가 있는 경우 생성자에 넣지 않고 인스턴스 초기화 블럭에 넣어 코드 중복을 줄일 수 있다.
DB Driver 구현에서 이 static 필드를 통해 DriverManager에 등록하는 것도 Class.forName을 통해 객체생성을 할 때 최초에 실행되는 것이 static 필드라서 그런 것 같다. static 필드는 동적으로 생성하든 정적으로 생성하든 제일 먼저 실행되기 때문이다.


JavaReflection
--------------

메모리에 올라온 클래스의 정보를 동적으로 가져와서 사용 가능하게 한다.
하지만 성능 이슈 때문에 꼭 필요할 경욱 ㅏ아니면 사용하지 않는다.



eclipse 플러그인 설치
-------------------

webclipse

[help - Eclipse Marketplace] -> webclipse 검색 -> 설치

자바스크립트 코드 자동완성 활성화
[Window - preference] -> JavaScript > Editor > Content Assist -> Enable auto activation 체크 후 저장


Mock Object란 무엇인가
-----------

[참고사이트](https://medium.com/@SlackBeck/mock-object%EB%9E%80-%EB%AC%B4%EC%97%87%EC%9D%B8%EA%B0%80-85159754b2ac)


테스트를 위해 사용되는 Object이다

```java
public class CellphoneMmsSender{
	private CellPhoneService cellPhoneService;

	public CellphoneMmsSender(CellPhoneService cellPhoneService){
    	this.cellPhonService = cellPhoneService;
    }

    public void send(String msg){
    	cellPhoneService.sendMMS(msg);
    }
}
```
다음과 같은 코드에서 ```CellphoneMmsSender의 ```send()``` 메서드에 대한 테스트 코드를 어떻게 작성하여야 할까? 반환값 검증도 하나의 방법이지만 여기서는 void를 반환하기 때문에 사용할 수 없다. 다른 방법은 없을까?

```CellphneMmsSender```의 ```send()``` 메서드에서 검증해야 하는 것은 전달받은 ```msg```를  ```CellphoneService.sendMMS```의 파라메터로 호출했는지 여부이다.
이를 위해 ```CellphoneMmsSender```가 참조하고 있는 ```CellphoneService``` 객체를 대역 객체로 대체하고 이를 검증하는 방법이 있다. 여기서 사용하는 가짜 객체를 ```Mock Object```라고 한다.

### Mock Object
Mock Object는 테스트 더블(Test Double) 중 하나이며 오브젝트의 행위를 테스트 한다. 여기서 Double은 대역이라는 의미를 가진다.
위의 예제에서 전달받은 ```msg```를 ```CellphoneService sendMMS()```의 파라메터로 호출했는지 여부가 행위에 해당한다. 이를 검증하며 테스트하는 것이 행위 검증이다.

### Mock Object를 사용한 테스트 코드

```java
public class CellPhoneServiceMock extends CellPhoneService{
	private boolean isSendMMSCalled = false;
    private String sendMsg = "";

    @Override
    public void sendMMS(String msg){
    	//행위 여부를 확인
        isSendMMSCalled = true;
        sendMsg = msg;
    }

    public boolean isSendMMSCalled() {
    	return isSendMMSCallled;
    }

    public String getSendMsg(){
    	return sendMsg;
    }
}
```
```java
public class CellphoneMmsSenderTest{
	@Test
    public void testSend() throws Exeption{
    	final String message = "Test Messgae";

        CellPhoneServiceMock cellPhnoeServiceMock = new CellPhoneServiceMock();

        CellphoneMmsSender cellphoneMmsSender = new CellphoneMmsSender(cellPhoneServiceMock);

        cellphoneMmsSender.send(message);

        Assert.assertTrue(cellPhoneServiceMock.isSendMMSCalled());
        Assert.assertEquals(message, cellPhoneServiceMock.getSendMsg());
    }
}
```
### Mockito를 사용한 테스트 코드
Mock Object를 직접 만들어 테스트할 수도 있지만 일일이 클래스를 만들고 관리하기는 부담스럽다. 이를 지원하는 라이브러리나 프레임워크가 있기 때문에 이를 이용하면 됩니다.

스프링 proxy
-----------


AutoCloseable
-------------


java6 이전까지는 close 메서드를 호출하여 안전하게 리소스를 닫아주어야 했다.

```java
//JAVA6 이전 ...
Class.forName("com.mysql.jdbc");
try {
	Class.forName("com.mysql.jdbc.Driver");
	conn = DriverManager.getConnection(jdbcUrl, urerId, passWord);
	ps = conn.prepareStatement(sql);

	//.....

} catch (SQLException e) {
	e.printStackTrace();
} finally {
	if (rs != null) {
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (ps != null) {
		try {
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	if (conn != null) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
```
JAVA7 부터는 명시적으로 ```close()```를 호출하지 않아도 자동으로 호출해 준다.
try with resource를 사용하면 자동으로 호출한다.


```JAVA
try (Scanner scanner = new Scanner(new File("test.txt"))) {
    while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException fnfe) {
    fnfe.printStackTrace();
}
```

[여기](https://docs.oracle.com/javase/8/docs/api/java/lang/AutoCloseable.html)에서 ```AutoCloseable```이 적용된 클래스들을 확인할 수 있다. ```Connection```과 ```PrepareStatment```, ```ResultSet```을 확인할 수 있다.

```java
public class FileInputStream implements AutoCloseable {
	private String file;
	public FileInputStream(String file){
		this.file = file;
	}
	public void read(){
		System.out.println(file+"을 읽습니다.");
	}
	@Override
	public void close() throws Exception {
		System.out.println(file +"을 닫습니다.");
	}
}
```
이처럼 직접 구현할 수도 있다.


<> 제네릭
---------

[참고링크](https://docs.oracle.com/javase/8/docs/technotes/guides/language/type-inference-generic-instance-creation.html)

jdbc management
---------------

[링크](http://gompangs.tistory.com/71)


객체 변경?
--------------

```java
package jave_test;

public class MainClass {

	public static void main(String[] args) {
		A a = new A();

		B temp = a.getBInstance();

		System.out.println("I got you! : " + temp.getnum());	//111

		temp.setnum(222);
		a.printB();//222
	}

}

class A {
	B b;

	public B getBInstance() {
		b = new B();
		b.setnum(111);
		return this.b;
	}

	public void printB() {
		System.out.println("b in A : " + b.getnum());
	}
}

class B {
	int num = 11;

	public int getnum() {
		return num;
	}

	public void setnum(int num) {
		this.num = num;
	}
}
```


이클립스 단축키
------------

- 오버라이드 ```Shift + Alt + S ```
- println : sysout 치고 ```ctrl + space```
- 빨간줄 처리방법 : ```ctrl + 1```



JAVA
----
- C/C++은 운영체제가 할당해주는 메모리를 어떻게 쓰느냐
- JAVA는 운영체제로부터 할당받은 JVM이 어떻게 쓰느냐이다.
- JAVA 프로그램은 JVM 위에서 돌아가는 것
- JVM도 하나의 프로그램

####JVM의 메모리
- 메소드 영역 : 메소드의 바이트코드, static 변수
- 스택 영역 : 지역변수 매개변수
- 힙 영역 : 인스턴스


접근제한자
---------

- public : 다른 패키지, 다른 클래스 모두 접근 가능
- default : 같은 패키지에서만 접근 가능, 같은 패키지 안에서는 다른 클래스나 상속을 통해서도 접근 가능. 다른 패키지는 상속으로도 접근 불가
- protected : 같은 패키지에서만 접근 가능, 다른 패키지에서 상속받을시 접근 가능
- private : 외부 접근 절대 불가능

자바 유틸 패키지 경로
-----------

```
C:\Java6\JRE6\lib\rt.jar 
```
여기를 열면 java.lang에 위치한걸 다 볼 수 있따

native 키워드
-----

Thread 클래스를 살펴보다가 ```native```라는 키워드를 봤다. 처음 보는 거라 검색해봤다.
```
native는 자바가 아닌 언어(보통 C나 C++)로 구현한 후 자바에서 사용하려고 할 때 이용하는 키워드이다. 자바로 구현하기 까다로운 것을 다른 언어로 구현해서, 자바에서 사용하기 위한 방법이다. 구현할때 JNI(Java Native Interface)를 사용한다.
```

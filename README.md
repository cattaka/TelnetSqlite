TelnetSqlite
============


## Want to peek SQLiteDatabase of non-rooted device

SQLiteDatabase of non-root device cannot be peeked in the development of Android apps. 
In a strict sense it is not be able to be peeked for security reason.
I wanted to run SQL directly during development. 
So I made a way to peek into force. 


## How? 

Embedding a small program that completed in one class into the app side.
I was to be able to access there via. 
Something like a back door short. 
Note that it should be removed in release build.


## Sample application

Application that was used in the description will be here. 
MyApplication.java and build.gradle of this application is target. 

* [FastCheckList] (https://github.com/cattaka/FastCheckList) 


## How to use 

The usage is the following three steps. 

* Embed a small program 
* Add code to kick the embedded program in the Application#onCreate method
* Execute the telnet and run SQL via small program

Let's take a look one by one. 

### Embed a small program 

Add the following line to the dependancies of build.gradle. 

`` `groovy 
     debugCompile 'net.cattaka: telnetsqlite: 1.0.0' 
`` `

You should use debugCompile because it is only for debugging purposes. 
Please keep in mind that if you use "compile" instead of "debugCompile", it would be included in the release build. 


### Add code to kick the embedded program in the Application#onCreate method

Add the code like the following to the custom application class. 
If you do not have a custom application class, please create it.

```java
public class MyAppliction extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Start server to access SQLite(only for development code)
        try {
            Class<?> clazz = Class.forName("net.cattaka.android.fastchecklist.TelnetSqliteService$TelnetSqliteServer");
            Thread serverThread = (Thread)clazz.getConstructor(Context.class, int.class).newInstance(this, 12080);
            serverThread.start();
        } catch (Exception e) {
            // ignore
        }
    }
}
```

A value of 12080 when generating a serverThread is the port number of the server. 
Please change the appropriate value if necessary. 
It is using reflection in order to avoid a compilation error in the release build.

## Using the GUI tools 

You can connect with telnet it can be useful, but it is hard to see molding also because it is not. There is a SQL editor GUI called [RdbAssistant]https://github.com/cattaka/RdbAssistant/releases) was corresponding Fortunately. Order to operate as a Java application, this SQL editor will work anywhere if environment where there is a Java runtime. 

* [RdbAssistant] (https://github.com/cattaka/RdbAssistant/releases) 

Let's make the connection information for the first time you start. Select the TelnetSqlite types of connection, please enter it is not wrong host name, port number, and database name. 

After you create the connection information, please press the Connect button to start. You will see input screen of SQL if you succeed to connect. You can just run the SQL, also browse the column name and a list of the table here. 


## Conclusion 

It is not impassable to avoid the use of SQLite database application development of Android. But just because the development is also very contents are in without peeking. Framework of Android Fortunately I can use the API a lot of standard, Chaimashou embedded by creating a small tool. You should be able to do the most if there is a ServerSocket. It was used in order to remove SQLiteDatabase this time, but this technique can also be applied to other things. However, because it is useless and is included in the release build, let's be careful not mixed.



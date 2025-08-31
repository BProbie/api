# EasyDB (简单好上手的新人数据库)

<br>
<br>


# Get started quickly! (快速上手)

## Download (下载地址)

## https://github.com/BProbie/api/tree/EasyDB/release

## https://github.com/BProbie/api/raw/refs/heads/EasyDB/release/EasyDB.jar

<br>

## Mainly remain (主要维护)

```java
Properties properties = new Properties();
```

```java
Supabase supabase = new Supabase();
```

<br>

## Local-Database (本地数据库)

```java
// Properties
// Based on java.util.Properties
Properties properties = new Properties();
properties.setValue("Name","Probie");
```

<br>

## Remot-Database (远程数据库)

```java
// Supabase
// Based on Supabase which based on Postgresql
Supabase supabase = new Supabase();
supabase.insert("Age",18);
```

<br>
<br>


# More nice! (更值得一提的)

## Support serialized storage! (支持序列化储存)

```java
// Define a data packet
public class DataPacket implements Serializable {
    private final String sex;
    
    public void setSex(String sex) {
        this.sex = sex;
        retrun this;
    }
    
    public String getSex() {
        return sex;
    }
}

// Get a data packet
DataPacket dataPacket = new DataPacket().setSex("middle");

// Serialize it!
Object data = SerializeBase64().getInstance.enSerializeToBase64(dataPacket);

// Store it!
Properties properties = new Properties();
properties.setValue("Sex",data);

// Get it!
Object packet = SerializeBase64().getInstance.deSerializeFromBase64(properties.getValue("Sex"));
```

<br>

## Chain to code! (链式编程)

```java
Properties properties = new Properties().setFileName("").setPath("").setFilePath("").setComment("");
```

```java
Supabase supabase = new Supabase().setDriver("").setUrl("").setUserName("").setPassword("").clone();
```

<br>

## Automatic resource recycling! (资源自动回收)

```java
// Properties
@Override
public boolean connect() {
	if (!getIsConnection()) {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
		return reConnect();
	 }
	 return true;
}

@Override
public void close() {
    // Synchronization Data
	commit();
}
```

```java
// Supabase
@Override
public boolean connect() {
	if (!getIsConnection()) {
		Runtime.getRuntime().addShutdownHook(new Thread(this::close));
		return reConnect();
	}
	return getIsConnection();
}

@Override
public void close() {
	if (getIsConnection()) {
        // Synchronization Data
		commit();
		try {
			getConnection().close();
		} catch (SQLException sqlException) {
			throw new RuntimeException(sqlException);
		}
	}
}

@Override
public boolean runPrepareCommand(PreparedStatement preparedStatement) {
	if (connect()) {
		if (connectTable()) {
			try {
				if (!preparedStatement.isClosed()) {
					boolean returnValue = preparedStatement.execute();
					preparedStatement.close();
					return returnValue;
				}
			} catch (SQLException sqlException) {
				throw new RuntimeException(sqlException);
			}
		}
	}
return false;
}
```

<br>

## Maximize the reduction of IO! (最大化减少IO开销)

```java
Properties properties = new Properties().setAutoCommit(false);
```

```java
Supabase supabase = new Supabase().setAutoCommit(false);
```

<br>

## More method! (更多方法)

```java
// Properties
Properties properties = new Properties();
properties.setValue();
properties.addValue();
properties.getValue();
properties.removeKey();
properties.removeValue();
```

```java
// Supabase
Supabase supabase = new Supabase();
supabase.insert();
supabase.runPrepareCommand();
supabase.runPrepareUpdata();
supabase.runPrepareUpdate();
supabase.runPrepareQuery();
```

<br>
<br>


# Last but not least (后记)

## API-Name: EasyDB

## API-Version: 1.0

## API-Language: Java

## API-Dependence: Maven   Postgresql-42.7.7

## JDK-Version: 21.0.8

## Author: Probie

## Thanks: []

<br>
<br>


# Learn More About (更多内容请关注)


# https://github.com/BProbie
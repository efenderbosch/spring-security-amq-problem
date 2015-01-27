Simply comment out

```java
@Bean
public ActiveMQConnectionFactory connectionFactory() {
	return new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
}
```

from Application.java to reproduce the issue.
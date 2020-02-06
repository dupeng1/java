在业务开发中我们会遇到形形色色的注解，但是框架自有的注解并不是总能满足负责的业务需求，
我们可以自定义注解来满足我们的需求。根据注解使用的位置，文章将分成字段注解、方法、类注解来介绍自定义注解
**字段注解**
字段注解一般用于校验字段是否满足要求，hibernate-validate依赖就提供了很多校验注解，如@NotNull、@Range等，
但是这些注解并不是能满足所有业务场景的。比如我们希望传入的参数在指定的String集合中，
那么已有的注解就不能满足需求了，需要自己实现
@Target 
定义注解的使用位置，用来说明注解可以被声明在哪些元素之前
ElementType.TYPE：说明该注解只能被声明在一个类前。
ElementType.FIELD：说明该注解只能被声明在一个类的字段前。
ElementType.METHOD：说明该注解只能被声明在一个类的方法前。
ElementType.PARAMETER：说明该注解只能被声明在一个方法参数前。
ElementType.CONSTRUCTOR：说明该注解只能声明在一个类的构造方法前。
ElementType.LOCAL_VARIABLE：说明该注解只能声明在一个局部变量前。
ElementType.ANNOTATION_TYPE：说明该注解只能声明在一个注解类型前。
ElementType.PACKAGE：说明该注解只能声明在一个包名前
@Retention
用来说明该注解类的生命周期。
RetentionPolicy.SOURCE: 注解只保留在源文件中
RetentionPolicy.CLASS : 注解保留在class文件中，在加载到JVM虚拟机时丢弃
RetentionPolicy.RUNTIME: 注解保留在程序运行期间，此时可以通过反射获得定义在某个类上的所有注解。
@Constraint
通过使用validatedBy来指定与注解关联的验证器
**方法、类注解**
在开发过程中遇到过这样的需求，如只有有权限的用户才能访问这个类中的方法或某个具体的方法、查找数据
的时候先不从数据库查找，先从guava cache中查找，再从redis查找，最后查找mysql（多级缓存）。这个时候
我们可以自定义注解去完成这个要求，第一个场景就是定义一个权限校验的注解，第二个场景就是定义
spring-data-redis包下类似@Cacheable的注解

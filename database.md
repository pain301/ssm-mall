## JDBC 模板
```java
public static void setUp(){
  String url = "";
  String username = "";
  String password = "";
  DriverManagerDataSource dataSource = new DriverManagerDataSource(url, username, password);
  dataSource.setDriverClassName("");

  JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
  jdbcTemplate.update("insert into t_user(name) values('name1')");
  jdbcTemplate.query("select * from t_user", new RowCallbackHandler(){
    @Override
    public void processRow(ResultSet rs) throws SQLException {
      System.out.println("id:" + rs.getInt("id"));
      System.out.println("name:" + rs.getString("name"));
    }
  });
}
```

### Mapper xml
#### 字符串拼接
```xml
<select id="selectUserByName" parameterType="java.lang.String" resultType="com.pain.po.User">
    SELECT * FROM USER WHERE username LIKE '%${value}%'
</select>
```
#### 主键自增返回
```xml
<insert id="insertUser" parameterType="com.pain.po.User">
  <selectKey key="id" resultType="int" order="AFTER">
    SELECT LAST_INSERT_ID()
  </selectKey>

  INSERT INTO USER
  (username, birthday, sex, address)
  VALUES (#{username}, #{birthday}, #{sex}, #{address})
</insert>
```
#### Mapper 代理
mapper 接口名与 mapper 映射文件 namespace 一致
mapper 接口方法名与 mapper 映射文件 statement id 一致
#### 自关联
```java
public class NewsLabel{
  private Integer id;
  private String name;
  private Set<NewsLabel> children;
}
```
```xml
<resultMap type="NewsLabel" id="newsLabelMap">
  <id column="id" property="id" />
  <result column="name" property="name" />
  <collection property="children"
              ofType="NewsLabel"
              select="selectChildrenByPid"
              column="id" />
</resultMap>

<!-- 递归调用 -->
<select id="selectChildrenByPid" resultType="newsLabelMap">
  select id, name from newslabel where pid=#{pid}
</select>
```
```xml
<resultMap type="NewsLabel" id="newsLabelMap">
  <id column="id" property="id" />
  <result column="name" property="name" />
  <collection property="parent"
              javaType="NewsLabel"
              select="selectParentById"
              column="pid" />
</resultMap>

<!-- 递归调用 -->
<select id="selectParentById" resultType="newsLabelMap">
  select id, name, pid from newslabel where id=#{id}
</select>
```
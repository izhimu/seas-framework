# Seas Framework

[![Maven Central](https://img.shields.io/badge/maven-v1.2.0-blue)](https://izhimu.coding.net/public-artifacts/seas/maven-releases-open/com.izhimu:seas-framework-bom/version/54230285/overview) [![License](https://img.shields.io/badge/license-Apache%202.0-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html) [![Java](https://img.shields.io/badge/Java-21%2B-orange.svg)](https://www.oracle.com/java/) [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5%2B-green.svg)](https://spring.io/projects/spring-boot)

**Seas Framework** 是一个专注效率的模块化开发框架，基于Spring Boot构建，提供完整的基础架构和丰富的功能组件，帮助开发者快速构建安全、稳定、高效的现代化应用。

## 🌟 核心特性

- **轻量级架构** - 模块化设计，按需引入，无冗余依赖
- **信创支持** - 全面支持国产化环境，适配信创生态
- **国密加密** - 内置国密算法支持，满足金融级安全要求
- **开发效率** - 代码生成、模板引擎、快速CRUD开发
- **多平台支持** - 支持Web、移动端、物联网设备等多端开发

## 🚀 快速开始

### 环境要求

- Java 21 或更高版本
- Maven 3.6 或更高版本
- MySQL 5.7+ 或 PostgreSQL 10+
- Redis 3.2+ (可选，用于缓存)

### 创建项目

在您的 `pom.xml` 中添加：

```xml
<repositories>
    <repository>
        <id>izhimu-seas-maven-releases-open</id>
        <name>maven-releases-open</name>
        <url>https://izhimu-maven.pkg.coding.net/repository/seas/maven-releases-open/</url>
    </repository>
</repositories>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>com.izhimu</groupId>
            <artifactId>seas-framework-dependencies</artifactId>
            <version>1.2.0</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>

<dependencies>
    <dependency>
        <groupId>com.izhimu</groupId>
        <artifactId>seas-framework-core-starter</artifactId>
    </dependency>
</dependencies>
```

### 启动应用

```java
@SpringBootApplication
@EnableSeasFramework
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

访问 http://localhost:8080 查看应用信息。

## 📦 功能组件

### 核心组件
- **seas-framework-core-starter** - 核心功能，自动配置，基础工具类
- **seas-framework-data-starter** - 数据访问，分页，多数据源
- **seas-framework-cache-starter** - 缓存抽象，Redis支持
- **seas-framework-captcha-starter** - 验证码，图形验证码，行为验证码

### 服务组件
- **seas-framework-base** - 基础服务，用户管理，权限管理
- **seas-framework-security** - 安全服务，认证授权，国密加密
- **seas-framework-storage** - 存储服务，文件上传，对象存储
- **seas-framework-healthy** - 健康服务，监控告警，健康检查
- **seas-framework-job** - 任务调度，分布式任务，定时任务
- **seas-framework-message** - 消息服务，站内信，邮件，短信
- **seas-framework-workflow** - 流程引擎，工作流，审批流
- **seas-framework-generate** - 代码生成，模板引擎，CRUD生成
- **seas-framework-mqtt** - MQTT服务，物联网通信，设备管理

## 🏗️ 技术栈

- **基础框架** - Spring Boot 3.5+
- **数据访问** - MyBatis Plus
- **安全框架** - Sa-Token, 国密SM2,SM4算法
- **缓存** - Redis, Caffeine

## 📖 文档

- [开发路线图](doc/DevelopmentRouteServer.md)

## 🤝 贡献指南

我们欢迎所有形式的贡献，包括但不限于：

- 🐛 [报告Bug](https://github.com/izhimu/seas-framework/issues)
- 💡 [提交新功能建议](https://github.com/izhimu/seas-framework/issues)
- 📖 [改进文档](https://github.com/izhimu/seas-framework/pulls)
- 🔧 [提交代码](https://github.com/izhimu/seas-framework/pulls)

### 开发环境搭建

1. Fork 项目到您的GitHub账号
2. Clone 到本地
```bash
git clone https://github.com/your-username/seas-framework.git
cd seas-framework
```

3. 创建开发分支
```bash
git checkout -b feature/your-feature-name
```

4. 提交代码
```bash
git add .
git commit -m "feat: add new feature"
git push origin feature/your-feature-name
```

5. 创建Pull Request

## 📄 许可证

Seas Framework 采用 Apache License 2.0 许可证，详见 [LICENSE](LICENSE) 文件。

## 💬 社区支持

- 📧 邮件群组：haoran@izhimu.cn
- 💬 微信社群：暂未开通
- 🐛 问题反馈：[GitHub Issues](https://github.com/izhimu/seas-framework/issues)
- 📖 官方文档：https://seas-framework.izhimu.com

## 🙏 致谢

感谢所有为Seas Framework做出贡献的开发者们！

特别感谢以下开源项目的支持：
- [Spring Boot](https://spring.io/projects/spring-boot)
- [MyBatis Plus](https://baomidou.com/)
- [Hutool](https://hutool.cn/)

---

<div style="text-align: center;">
  <p>如果这个项目对您有帮助，请给我们一个 ⭐️ Star！</p>
  <p><a href="https://github.com/your-org/seas-framework">GitHub Repository</a></p>
</div>

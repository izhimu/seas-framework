# 贡献指南

感谢您对Seas Framework的兴趣！我们欢迎所有形式的贡献，包括错误报告、功能建议、文档改进和代码贡献。

## 开始之前

在贡献之前，请确保您：
2. 阅读了[README.md](README.md)了解项目
3. 有一个GitHub账号

## 贡献方式

### 🐛 报告Bug
如果您发现了Bug，请通过以下方式报告：

1. **搜索现有问题**：在创建新问题前，请先搜索[现有的Issues](https://github.com/your-org/seas-framework/issues)
2. **创建Issue**：使用Bug报告模板创建新的Issue
3. **提供详细信息**：包括：
   - 使用的Seas Framework版本
   - Java版本
   - 操作系统
   - 重现步骤
   - 期望行为与实际行为
   - 相关日志或错误信息

### 💡 功能建议
如果您有新功能建议：

1. **搜索现有建议**：查看是否已有类似建议
2. **创建Feature Request**：使用功能请求模板
3. **描述使用场景**：说明为什么需要这个功能
4. **提供实现思路**（可选）：如果您有实现想法，请分享

### 📖 文档改进
文档改进包括：
- 修复错别字或语法错误
- 改进说明清晰度
- 添加示例代码
- 更新过时的信息

### 🔧 代码贡献

#### 开发环境设置

1. **Fork项目**
   ```bash
   # 在GitHub上Fork项目到您的账号
   ```

2. **克隆项目**
   ```bash
   git clone https://github.com/your-username/seas-framework.git
   cd seas-framework
   ```

3. **添加上游仓库**
   ```bash
   git remote add upstream https://github.com/your-username/seas-framework.git
   ```

4. **创建开发分支**
   ```bash
   git checkout -b feature/your-feature-name
   # 或
   git checkout -b fix/issue-number-description
   ```

#### 开发规范

##### 代码风格
- 使用Java 21语法特性
- 遵循Google Java Style Guide
- 使用4个空格缩进，不使用tab
- 最大行宽120字符

##### 提交信息
使用[Conventional Commits](https://www.conventionalcommits.org/)规范：

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

类型包括：
- `feat`: 新功能
- `fix`: Bug修复
- `docs`: 文档更新
- `style`: 代码格式调整
- `refactor`: 代码重构
- `test`: 测试相关
- `chore`: 构建/辅助工具

示例：
```
feat(security): add SM4 encryption support

Add support for SM4 symmetric encryption algorithm to meet
national cryptography requirements.

Closes #123
```

##### 代码质量
- 所有新代码必须有单元测试
- 测试覆盖率不低于80%
- 通过所有现有测试
- 遵循[Checkstyle](https://checkstyle.org/)规则

#### 开发流程

1. **同步上游**
   ```bash
   git fetch upstream
   git checkout main
   git merge upstream/main
   ```

2. **创建功能分支**
   ```bash
   git checkout -b feature/your-feature
   ```

3. **开发和测试**
   ```bash
   # 编写代码
   # 添加测试
   mvn clean test
   ```

4. **提交更改**
   ```bash
   git add .
   git commit -m "feat(scope): description"
   ```

5. **推送分支**
   ```bash
   git push origin feature/your-feature
   ```

6. **创建Pull Request**
   - 在GitHub上创建PR
   - 填写PR模板
   - 等待代码审查

#### 测试要求

- **单元测试**：每个新功能必须有对应的单元测试
- **集成测试**：重要功能需要集成测试
- **示例代码**：复杂功能需要提供示例

运行测试：
```bash
mvn clean test
mvn clean verify
```

#### 构建检查
在提交前请运行：
```bash
mvn clean compile
mvn clean test
mvn clean package
```

## 代码审查

所有PR都需要至少一个维护者审查：

1. **自动检查**：CI/CD会运行测试和代码质量检查
2. **人工审查**：维护者会审查代码质量和设计
3. **修改建议**：根据审查意见修改代码
4. **合并**：审查通过后合并到主分支

## 发布流程

我们使用语义化版本：
- `MAJOR.MINOR.PATCH`格式
- 破坏性变更增加MAJOR
- 向后兼容的功能增加MINOR
- Bug修复增加PATCH

## 联系维护者

如果您有任何问题或需要帮助：
- 创建GitHub Issue
- 发送邮件至：haoran@izhimu.cn
- 加入微信社群

## 致谢

感谢所有[贡献者](https://github.com/izhimu/seas-framework/graphs/contributors)！

---

**再次感谢您的贡献！** 🙏
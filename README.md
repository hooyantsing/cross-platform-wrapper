# m3u8-client

> 简单易用的 [N_m3u8DL-RE](https://github.com/nilaoda/N_m3u8DL-RE) 跨平台 Java 包装器。

[![Java](https://img.shields.io/badge/java-8-ae7118.svg?style=flat-square)](https://www.oracle.com/cn/java/technologies)

### 平台特性

* 项目简单，小白都能读懂源码；
* 依据不同操作系统，自动匹配可用客户端；
* 方便扩展，不仅于某款软件，实际上万物皆可包装。

### 使用说明

> 详见 **m3u8-client-example** 子工程。

按需在 Maven pom 文件引入依赖：

```
// 例如我希望支持 Windows 和 MacOS 操作系统

<dependency>
    <groupId>xyz.hooy</groupId>
    <artifactId>m3u8-client-nativebin-n_m3u8dl_re-win-x64</artifactId>
    <version>${LASTEST_VERSION}</version>
</dependency>
<dependency>
    <groupId>xyz.hooy</groupId>
    <artifactId>m3u8-client-nativebin-n_m3u8dl_re-osx-arm</artifactId>
    <version>${LASTEST_VERSION}</version>
</dependency>
```

调用代码样例：

```
GeneralLocator locator = new GeneralLocator("N_m3u8DL-RE", "v0.2.0-beta");
Executor executor = locator.createExecutor();
executor.addArguments("https://m3u8-client.hooy.xyz/example.m3u8");
executor.execute();
executor.destroy();
```

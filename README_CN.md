# WorldGuard 中文版 (WorldGuard-cn)

基于 [EngineHub/WorldGuard](https://github.com/EngineHub/WorldGuard) 7.0.x 分支的简体中文汉化版本。

## 功能

WorldGuard 让你和玩家可以保护土地免受破坏，并调整/禁用 Minecraft 的多种游戏功能：

- 阻止苦力怕、凋灵等造成的方块破坏
- 禁用火焰蔓延、岩浆引燃、冰形成、末影人搬方块等
- 黑名单特定物品和方块
- 保护区域，仅允许特定玩家建造
- 设置 PVP、TNT、生物伤害等功能的禁用区域
- 防止各种漏洞利用
- 所有功能默认关闭，按需启用

## 版本对照

| 分支 | 版本号 | 适用 Minecraft | 产物 |
|------|--------|----------------|------|
| `version/7.0.x` | `7.0.18-cn-SNAPSHOT` | 1.21.10+（当前 Paper API） | `worldguard-bukkit-7.0.18-cn-SNAPSHOT-dist.jar` |
| `version/7.0.12-cn-1.21.1` | `7.0.12-cn` | 1.21 – 1.21.1（需 WorldEdit 7.3.x） | `worldguard-bukkit-7.0.12-cn-dist.jar` |

## 汉化内容

完整 I18N 体系，玩家可见文案均来自独立语言文件（默认 `zh_CN`，`en` 作回退）：

- 区域拒绝消息（含动作描述，不再中英混合）
- 命令输出、异常提示、黑名单消息
- `/rg info` 等交互式 UI 文本
- 命令帮助描述（`/wg`、`/rg` 等）

区域名、玩家名、材质 ID、用户输入等动态内容保持原样，不翻译。

## 语言配置

首次启动后，插件数据目录结构如下：

```
plugins/WorldGuard/
  config.yml          # language: zh_CN（可改为 en）
  lang/
    zh_CN.yml         # 简体中文（可自定义）
    en.yml            # 英文回退
```

修改 `config.yml` 中的 `language` 后执行 `/wg reload` 即可热重载语言包。已有 `lang/*.yml` 不会被覆盖，便于服主自行维护翻译。

## 依赖

- [Paper](https://papermc.io) 或兼容的 Bukkit 服务端
- [WorldEdit](https://dev.bukkit.org/projects/worldedit) 插件

## 下载

构建产物可在 Codeberg Actions 的 Artifacts 中下载，或使用 `gradlew build` 本地编译。

编译后插件 JAR 位于：`worldguard-bukkit/build/libs/worldguard-bukkit-*-dist.jar`

## 编译

```bash
./gradlew build
```

需要 Java 21 或更高版本。

## 许可证

GNU Lesser General Public License v3

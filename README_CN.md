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

## 汉化内容

- 默认拒绝消息（deny-message、entry-deny-message、exit-deny-message）
- 权限与保护相关提示
- 命令描述（/rg、/wg 等）
- 配置文件默认提示

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

<a name="readme-top"></a>

<br />
<div align="center">
  <a href="https://github.com/carlrobertoh/CodeGPT">
    <img alt="plugin-icon" src="docs/assets/icon.png">
  </a>
  <h1 style="margin: 0;" align="center">CodeGPT</h1>
  <p>
    A JetBrains extension that allows you to use ChatGPT inside your favourite IDE
  </p>
</div>

[![Downloads][downloads-shield]][plugin-repo]
[![Rating][Rating-shield]][plugin-repo]
[![Version][version-shield]][plugin-repo]
[![Contributions not available][contributions-not-available-svg]][contributions-not-available]

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li><a href="#about-the-project">About The Project</a></li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
        <li><a href="#api-key-configuration">API Key Configuration</a></li>
      </ul>
    </li>
    <li><a href="#features">Features</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
  </ol>
</details>

## About The Project

This is an extension for JetBrains IDEs that integrates ChatGPT into your coding environment.
By leveraging the power of GPT-3, this makes it an invaluable tool for developers looking to streamline their workflow and gain a deeper understanding of the code they're working on.

## Getting Started

To get started, follow these simple steps:

### Prerequisites

In order to use the extension, you need to have a JetBrains IDE installed and the API key configured.
You can find the API key in your [User settings][api-key-url].

### Installation

The plugin is available from [JetBrains Marketplace][plugin-repo].
You can install it directly from your IDE via the `File | Settings/Preferences | Plugins` screen.
On the `Marketplace` tab simply search for `codegpt` and select the `CodeGPT` suggestion:

![marketplace][marketplace-img]

### API Key Configuration

After the plugin has been successfully installed, the API key needs to be configured.

You can configure the key by going to the plugin's settings via the `File | Settings/Preferences | Tools | CodeGPT`.
On the settings panel simply click on the `API key` field, paste the key obtained from the OpenAI website and click `Apply/OK`:

![plugin-settings][plugin-settings]

#### Azure OpenAI configuration
Specifically for Azure OpenAI services, you will have to input three supplementary fields:
* the `Resource Name`, which is the name of your Azure OpenAI Cognitive Services. It's the first part of the url you're provided to use the service: `https://my-resource-name.openai.azure.com/` -> use `my-resource-name`. You can find it in your Azure Cognitive Services page, under `Resource Management` -> `Resource Management` -> `Keys and Endpoints`.
* the `Deployment ID`, which is the name of your Deployment. You can find it in the Azure AI Studio, under `Management` -> `Deployment` -> `Deployment Name` column in the table.
* the `API Version`, I usually used the last non-preview version, which is currently `2023-05-15`. 
In addition to these, you need to input one of the two API Keys provided, found along with the `Resource Name`.

## Features

The plugin provides several key features, such as:

### Ask Anything

Ask anything you'd like.

<p align="center">
  <img src="docs/assets/gif/ask-anything.gif" alt="animated" />
</p>

### Select and Ask

Ask anything related to your selected code.

<p align="center">
  <img src="docs/assets/gif/custom-prompt.gif" />
</p>

### Replace Generated Code

Instantly replace a selected code block in the editor with suggested code generated by AI.

<p align="center">
  <img src="docs/assets/gif/replace-code.gif" />
</p>

### Regenerate Response

Expected a different answer? Re-generate any response of your choosing.

<p align="center">
  <img src="docs/assets/gif/regenerate.gif" />
</p>

### Other features

- **Conversation History** - View recent conversation history and restore previous sessions, making it easy to pick up where you left off
- **Concurrent conversations** - Chat with the AI in multiple tabs simultaneously
- **Seamless conversations** - Chat with the AI regardless of the maximum token limitations
- **Predefined Actions** - Create your own editor actions or override the existing ones, saving time rewriting the same prompt repeatedly

## Roadmap

- [x] Add proxy support
- [ ] Add conversation history
    - [x] Ability to start/restore sessions
    - [ ] Ability to export conversations in Markdown/JSON format
- [ ] Add codex and user's fine-tuned models
- [x] Ability to have a seamless conversation despite to token limitation
- [x] Add support for copying and replacing generated code snippets
- [x] Add support for deleting previous conversations  
- [x] Add support for overriding prompts and request params
- [x] Add Azure OpenAI service support
- [x] Add action key mappings
- [ ] Add support for code search using embeddings 
- [ ] Add support for model fine-tuning

See the [open issues][open-issues] for a full list of proposed features (and known issues).

## Development
**core class**

```xml
<extensions defaultExtensionNs="com.intellij">
<!--    插件启动-->
    <postStartupActivity implementation="ee.carlrobert.codegpt.PluginStartupActivity"/>
<!--    配置项tools-codegpt-->
    <applicationConfigurable id="settings.codegpt" parentId="tools" displayName="CodeGPT"
                             instance="ee.carlrobert.codegpt.state.settings.SettingsConfigurable"/>
<!--    配置项tools-codegpt-configuration-->
    <applicationConfigurable id="settings.codegpt.configuration" parentId="settings.codegpt" displayName="Configuration"
                             instance="ee.carlrobert.codegpt.state.settings.configuration.ConfigurationConfigurable"/>
<!--    配置项tools-codegpt-advanced-->
    <applicationConfigurable id="settings.codegpt.advanced" parentId="settings.codegpt" displayName="Advanced Settings"
                             instance="ee.carlrobert.codegpt.state.settings.advanced.AdvancedSettingsConfigurable"/>
<!--    服务settingsState-->
    <applicationService serviceImplementation="ee.carlrobert.codegpt.state.settings.SettingsState"/>
<!--    服务configurationState-->
    <applicationService serviceImplementation="ee.carlrobert.codegpt.state.settings.configuration.ConfigurationState"/>
<!--    服务advanceSettingsState-->
    <applicationService serviceImplementation="ee.carlrobert.codegpt.state.settings.advanced.AdvancedSettingsState"/>
<!--    服务conversationState-->
    <applicationService serviceImplementation="ee.carlrobert.codegpt.state.conversations.ConversationsState"/>
<!--    项目服务chatContentManagerService-->
    <projectService serviceImplementation="ee.carlrobert.codegpt.toolwindow.chat.ChatContentManagerService"/>
<!--    工具栏-->
    <toolWindow id="CodeGPT" icon="Icons.ToolWindowIcon" anchor="right"
                factoryClass="ee.carlrobert.codegpt.toolwindow.ProjectToolWindowFactory"/>
</extensions>
```





* action - 右键菜单动作
  * ActionsUtil.java - 注册、刷新
  * AskAction.java - 注册Ask ChatGpt动作
  * BaseAction.java 
  * CustomPromptAction.java 
  * CustomPromptDialog.java - 自定义提示词会话界面
* client
  * ActionListener.java 
  * ClientProvider.java - openai客户端
  * CompletionRequestProvider.java - 构建openai请求消息
  * EventListener.java - openai响应结果处理
  * RequestHandler.java - 封装请求重试、取消
  * TotalUsageExceededException.java
* state
  * conversations
    * converter
    * message
    * Conversation.java 
    * ConversationsContainer.java 
    * ConversationsState.java - 会话状态
  * settings
    * advanced - 次级设置状态2
    * components - 子模块界面绘制
    * configuration - 次级设置状态1
    * BaseModelComboBox.java 
    * SettingsComponent.java - 顶级设置状态 - 绘制界面
    * SettingsConfigurable.java - 顶级设置状态 - 数据与界面交互
    * SettingsState.java - 顶级设置状态 - 数据
* toolwindow - swing绘制界面
  * chat
    * action
    * html
    * swing
    * ChatContentManagerService.java - 会话内容门面类
    * ChatTabbedPane.java
    * ChatToolWindowPanel.java
    * CloseableTabButton.java
    * ToolWindowTabPanel.java
    * ToolWindowTabPanelFactory.java
  * components
  * conversations - 工具栏窗口、会话窗口界面
  * ProjectToolWindowFactory.java
* util
  * FileUtils.java 
  * SwingUtils.java 
  * ThemeUtils.java
* EncodingManager.java
* PluginStartupActivity.java - 插件启动

## License

MIT © [Carl-Robert Linnupuu][portfolio]

If you found this project interesting, kindly rate it on the marketplace and don't forget to give it a star. Thanks again!
<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[downloads-shield]: https://img.shields.io/jetbrains/plugin/d/21056-codegpt
[version-shield]: https://img.shields.io/jetbrains/plugin/v/21056-codegpt?label=version
[rating-shield]: https://img.shields.io/jetbrains/plugin/r/rating/21056-codegpt
[contributions-not-available-svg]: https://img.shields.io/badge/Contributions-Currently%20Unavailable-yellow
[contributions-not-available]: #
[marketplace-img]: docs/assets/marketplace.png
[plugin-repo]: https://plugins.jetbrains.com/plugin/21056-codegpt
[plugin-settings]: docs/assets/plugin-settings.png
[open-issues]: https://github.com/carlrobertoh/CodeGPT/issues
[api-key-url]: https://platform.openai.com/account/api-keys
[portfolio]: https://carlrobert.ee

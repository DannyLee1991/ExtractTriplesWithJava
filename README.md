## 三元组抽取

使用hanlp抽取的句法依存关系提取三元组

## 使用方式

下载[data.zip](http://nlp.hankcs.com/download.php?file=data)

解压后的`data`目录放置在`./resources`目录下

修改`./src/hanlp.properties`中的`root`,改为你当前的`resources`目录的绝对路径地址

将需要提取三元组的`sentences.txt`文件放在`resources`目录下(`sentences.txt`中是包含若干个句子的文本文件，逐行分开，中间不空行)

拉取maven配置后，运行`Main.java`即可
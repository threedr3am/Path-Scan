Java版的web路径扫描器，可配置线程数（默认32线程），以及连接超时、读取超时时间（在网络情况糟糕时自定义配置，默认500ms）

使用方法：

1. 配置CONFIG.ini文件，threads为线程数，connectTimeout为连接超时时间，readTimeout为读取超时时间

2. 在dict文件夹放置字典文件，默认读取dict所有字典，可通过配置文件CONFIG.ini参数dict配置指定读取的字典文件（多个文件间以英文逗号分割）

3. 执行java -jar Path-Scan.jar http://www.baidu.com
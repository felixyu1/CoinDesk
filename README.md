# Cathay Bank Trial Project
國泰世華Java Engineer線上作業   
## 一、專案簡述   
利用 maven 建置一個 Spring-boot 專案，實作指定功能，將結果上傳至
gitHub，並提供國泰世華連結，以供評審。   

## 二、指定功能簡述   
1.呼叫 coindesk API，解析其下行內容與資料轉換，並實作新的 API。
coindesk API：https://api.coindesk.com/v1/bpi/currentprice.json

2.建立一張幣別與其對應中文名稱的資料表（附建立 SQL 語法），並提
供 查詢 / 新增 / 修改 / 刪除 功能 API。   

## 三、開發者   
Felix Yu (YU SIH HAO) 宇斯豪   
E-mail: felixyu.think@gmail.com   

## 四、開發環境與使用工具   
* Java 8
* Spring Boot 2.7.8
* H2 database
* Spring Data JPA
* IDE: IntelliJ IDEA Ultimate Edition 11.0.15
* Git for Windows v2.35.3
* Github
* Maven
* JUnit 5
* ModelMapper
* Postman v10.10.0
* Spring Restdocs

## 五、實作說明   
1. 採MVC Controller-Service-Dao 模式   
2. Java Packages簡述      
   > com.cathaybk.coindesk
   >>**controller**: Controller層   
   >>**service**: Service層   
   >>**dao**: DAO層   
   >>**dto**: REST API接收和傳回的資料物件DTO   
   >>**model**: Service層接收和傳回的資料物件Model   
   >>**mapper**: 資料物件間的對應/轉換   
   >>**exception**: 自訂Exception
   >>>**handler**: Controller層的Exception Handler   
3. 資料表：建立H2 DB table: COIN_DESC 為**幣別資料表**, DDL請參看 [src/main/resources/schema.sql](src/main/resources/schema.sql)   
4. 線上作業要求實作內容與method的對應   
   - 幣別資料新增：com.cathaybk.coindesk.controller.CoinController.createCoinDesc   
   - 幣別資料修改：com.cathaybk.coindesk.controller.CoinController.updateCoinDesc   
   - 幣別資料刪除：com.cathaybk.coindesk.controller.CoinController.deleteCoinDesc   
   - 幣別資料查詢(依據幣別代號)：com.cathaybk.coindesk.controller.CoinController.getCoinDesc   
   - 讀取所有幣別資料：com.cathaybk.coindesk.controller.CoinController.getAllCoinDesc   
   - 呼叫CoinDesk 的API: com.cathaybk.coindesk.service.getBpisFromCoinDesk   
   - 呼叫CoinDesk 的API, 並進行資料轉換，組成新 API: com.cathaybk.coindesk.controller.CoinController.getBPIs   
## 六、單元測試   
### Classes   
* src/test/java/com.cathaybk.coindesk.controller.CoinControllerTest   
* src/test/java/com.cathaybk.coindesk.service.impl.CoinServiceImplTest   
### 各功能的單元測試methods   
1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容   
- 查到一筆資料的測試：CoinControllerTest的getCoinDesc_success   
  `測試通過條件：傳回status code 200, 輸入存在於對應表的幣別"JPY"得到"日圓"`   
- 無法查到資料的測試：CoinControllerTest的getCoinDesc_notFound   
  `測試通過條件：傳回status code 404, 輸入不存在於對應表的幣別"XXX"得到CoinDescNotFoundException的拋出`   
2. 測試呼叫新增幣別對應表資料 API   
- 新增幣別對應資料成功的測試：CoinControllerTest的createCoinDesc_success   
  `測試通過條件：傳回status code 201`   
- 新增幣別對應資料違反Primary Key的測試：CoinControllerTest的createCoinDesc_createDuplicated   
  `測試通過條件：傳回status code 400, 得到CoinDescFoundException的拋出`   
3. 測試呼叫更新幣別對應表資料 API，並顯示其內容   
- 更新資料成功的測試：CoinControllerTest的updateCoinDesc_success   
  `測試通過條件：傳回status code 200`   
- 無法找到欲更新的幣別對應資料測試：CoinControllerTest的updateCoinDesc_updateNonExisting   
  `測試通過條件：傳回status code 404, 得到CoinDescNotFoundException的拋出`   
4. 測試呼叫刪除幣別對應表資料 API   
- 刪除資料成功的測試：CoinControllerTest的deleteCoinDesc_success   
  `測試通過條件：傳回status code 204`   
- 無法找到欲刪除的幣別對應資料測試：CoinControllerTest的deleteCoinDesc_deleteNonExisting   
  `測試通過條件：傳回status code 404, 得到CoinDescNotFoundException的拋出`   
5. 測試呼叫 coindesk API，並顯示其內容   
- 呼叫成功的測試：CoinServiceImplTest的getBpisFromCoinDesk   
  `測試通過條件：呼叫CoinDesk API後得到的BpisPojo物件不為NULL`   
6. 測試呼叫資料轉換的 API，並顯示其內容   
- 呼叫且轉換成功的測試：CoinControllerTest的getBPIs   
  `測試通過條件：傳回status code 200`   
### 備註
單元測試存取的H2 table COIN_DESC在測試起始時即會放入一筆幣別為"JPY"的資料 (參看:[src/test/resources/data.sql](src/test/resources/data.sql))   

## 七、REST API   
API列表、呼叫方式及Request/Response samples 請以Web Browser開啟：[target/generated-docs/index.html](target/generated-docs/index.html)   


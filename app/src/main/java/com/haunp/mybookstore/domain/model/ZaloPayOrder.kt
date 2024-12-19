package com.haunp.mybookstore.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.haunp.mybookstore.domain.constant.AppInfo
import com.haunp.mybookstore.domain.helper.Helpers
import java.util.Date

@Entity
data class ZaloPayOrder(
    @PrimaryKey
    var appId: String = AppInfo.APP_ID.toString(),
    var appUser: String = "MyBookStore",
    var appTime: String = Date().time.toString(),
    var amount: String,
    var appTransId: String = Helpers.getAppTransId(),
    var embedData: String = "{\"promotioninfo\":\"\",\"merchantinfo\":\"embeddata123\"}",
    var items: String = "[{\"itemid\":\"knb\",\"itemname\":\"kim nguyen bao\",\"itemprice\":198400,\"itemquantity\":1}]",
    var bankCode: String = "zalopayapp",
    var description: String = "Merchant pay for order #${Helpers.getAppTransId()}",
    var mac: String = Helpers.getMac(
        AppInfo.MAC_KEY,
        data = "$appId|$appTransId|$appUser|$amount|$appTime|$embedData|$items"
    )
)
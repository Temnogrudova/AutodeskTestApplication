package com.autodesk.ekaterinatemnogrudova.autodesktestapplication.robots

import com.autodesk.ekaterinatemnogrudova.autodesktestapplication.R


class ArticlesRobot: BaseRobot() {

    fun matchToolbarDisplayedForText(res: String) = apply {
        matchTextToolBar(view(R.id.tool_bar), res)
    }

    fun clickOnArticle(position: Int) = apply {
        clickListItem(view(R.id.articles_list), position)
    }
}
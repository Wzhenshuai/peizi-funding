/**
 * Created by codec on 2017/5/2.
 */

/*
 * 说明：将指定下拉列表的选项值清空
 * 作者：
 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
 */
function removeOptions(selectObj){
    if (typeof selectObj != 'object')
    {
        selectObj = document.getElementById(selectObj);
    }
    var lenl = selectObj.options.length;
    for (var i=0; i < lenl; i++)
    {
        // 移除当前选项
        selectObj.options[0] = null;
    }
}
/*
 * 说明：设置传入的选项值到指定的下拉列表中
 * 作者：
 * @param {String || Object]} selectObj 目标下拉选框的名称或对象，必须
 * @param {Array} optionList 选项值设置 格式：[{txt:'北京', val:'010'}, {txt:'上海', val:'020'}] ，必须
 * @param {String} firstOption 第一个选项值，如：“请选择”，可选，值为空
 * @param {String} selected 默认选中值，可选
 */
function setSelectOption(selectObj, optionList, firstOption, selected)
{
    if (typeof selectObj != 'object')
    {
        selectObj = document.getElementById(selectObj);
    }
    removeOptions(selectObj); // 清空选项
    var start = 0;   // 选项计数
    var len = optionList.length;
    if (firstOption)  // 如果需要添加第一个选项
    {
        selectObj.options[0] = new Option(firstOption, '');
        start ++;   // 选项计数从 1 开始
    }
    for (var i=0; i < len; i++)
    {

        selectObj.options[start] = new Option(optionList[i].txt, optionList[i].val); // 设置 option
        if(selected == optionList[i].val)  // 选中项
        {
            selectObj.options[start].selected = true;
        }
        start ++;  // 计数加 1
    }
}

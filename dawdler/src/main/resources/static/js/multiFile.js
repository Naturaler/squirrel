// 新增单个上传按钮
function oneMoreFile() {
    var ddlMetadata = document.getElementById("fileList");
    var newRow = ddlMetadata.insertRow();
    newRow.innerHTML = "";
}

/**
 * 切换tab内容
 * @param showOnId 展示内容页面id
 * @param showOffId 隐藏内容页面id
 * @param tabButtonSelected 选中tab按钮id
 * @param tabButtonDef 未选中tab按钮id
 */
function switch_tab(showOnId, showOffId, tabButtonSelected, tabButtonDef) {
    var showOn = document.getElementById(showOnId);
    showOn.className = "show_on";

    var showOff = document.getElementById(showOffId);
    showOff.className = "show_off";

    var selectedTabButton = document.getElementById(tabButtonSelected);
    selectedTabButton.className = "tab_button_selected";

    var defTabButton = document.getElementById(tabButtonDef);
    defTabButton.className = "tab_button_def";
}
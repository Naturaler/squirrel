// 增加 ddl 行
function addDdlRow() {
    var ddlMetadata = document.getElementById("ddlMetadata");
    var newRow = ddlMetadata.insertRow();

    // 获取当前行号（包括表头）
    var rowSize = ddlMetadata.rows.length;
    var newRowIndex = rowSize;

    newRow.innerHTML = '<td>' + (newRowIndex - 2) + '</td>' +
        '<td><input name="ddlList[' + newRowIndex + '].ddlType" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].schema" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].table" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].metadataBefore" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].metadataAfter" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].column" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].columnType" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].primaryKey" value=""></td>\n' +
        '<td><input name="ddlList[' + newRowIndex + '].remark" value=""></td>\n' +
        '<td><input type="button" value="删除行" onclick="deleteRow(\'ddlMetadata\', this)" class="inline_button" value=""></td>';
}

// 增加dml 行
function addDmlRow() {
    var dmlMetadata = document.getElementById("dmlMetadata");
    var newRow = dmlMetadata.insertRow();

    // 获取当前行号（包括表头）
    var rowSize = dmlMetadata.rows.length;
    var newRowIndex = rowSize;

    newRow.innerHTML = '<td>' + (newRowIndex - 2) + '</td>\n' +
        '<td><input name="dmlList[' + newRowIndex + '].dmlType" value=""></td>\n' +
        '<td><input name="dmlList[' + newRowIndex + '].schema" value=""></td>\n' +
        '<td><input name="dmlList[' + newRowIndex + '].table" value=""></td>\n' +
        '<td><input name="dmlList[' + newRowIndex + '].column" value=""></td>\n' +
        '<td><input name="dmlList[' + newRowIndex + '].remark" value=""></td>\n' +
        '<td><input type="button" value="删除行" onclick="deleteRow(\'dmlMetadata\', this)" class="inline_button" value=""></td>';
}

// 删除行
function deleteRow(tableId, rowObj) {
    var tableObj = document.getElementById(tableId);
    tableObj.deleteRow(rowObj.parentNode.parentNode.rowIndex);
}
/**
 * Created by s-sumi on 2016/03/24.
 */
var endpoint='';
(function() {
    /*
     http://www.html5rocks.com/ja/tutorials/file/dndfiles/
     http://www.pori2.net/html5/File/040.html
     */
    var print_img_id = 'print_img';
    var print_DataURL_id = 'print_DataURL';
    if (checkFileApi()){
        //ドラッグオンドロップ
        var dropZone = document.getElementById('drop-zone');
        dropZone.addEventListener('dragover', handleDragOver, false);
        dropZone.addEventListener('drop', handleDragDropFile, false);
    }
    // FileAPIに対応しているか
    function checkFileApi() {
        // Check for the various File API support.
        if (window.File && window.FileReader && window.FileList && window.Blob) {
            // Great success! All the File APIs are supported.
            return true;
        }
        alert('The File APIs are not fully supported in this browser.');
        return false;
    }
    //ファイルが選択されたら読み込む
    function selectReadfile(e) {
        var file = e.target.files;
        var reader = new FileReader();
        //dataURL形式でファイルを読み込む
        reader.readAsDataURL(file[0]);
        //ファイルの読込が終了した時の処理
        reader.onload = function(){
            readImage(reader, print_img_id, print_DataURL_id);
        }
    }
    //ドラッグオンドロップ
    function handleDragOver(e) {
        e.stopPropagation();
        e.preventDefault();
        e.dataTransfer.dropEffect = 'copy'; // Explicitly show this is a copy.
    }
    function handleDragDropFile(e) {
        e.stopPropagation();
        e.preventDefault();
        var files = e.dataTransfer.files; // FileList object.
        var file = files[0];
        var reader = new FileReader();
        //dataURL形式でファイルを読み込む
        reader.readAsDataURL(file);
        //ファイルの読込が終了した時の処理
        reader.onload = function(){
            readImage(reader, print_img_id, print_DataURL_id);
        }
    }
    //ファイルの読込が終了した時の処理
    function readImage(reader, print_image_id, print_DataURL_id ){
        //ファイル読み取り後の処理
        var result_DataURL = reader.result;
        //読み込んだ画像とdataURLを書き出す
        var img = document.getElementById('image');
        var src = document.createAttribute('src');
        src.value = result_DataURL;
        img.setAttributeNode(src);
        document.getElementById(print_DataURL_id).value = result_DataURL;
        printWidthHeight('image', 'width-height');
        var jsn={'base64':result_DataURL.replace('data:image/jpeg;base64,',"")};
        $.ajax({
            url: endpoint+'getAA',
            dataType: 'application/json',
            data: jsn,
            type: 'POST',
            success: function(str){
                $('#editor').text(str);
            }
        });


    }
    //width, height表示
    function printWidthHeight( img_id, width_height_id ) {
        var img = document.getElementById(img_id);
        var w = img.naturalWidth;
        var h = img.naturalHeight;
        document.getElementById(width_height_id).innerHTML = 'width:' + w + ' height:' + h;
    }
})();
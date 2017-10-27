/**
 * Created by ken on 2017/4/4.
 */
//环境配置
//var ENV = 'TEST';
var ENV = 'BUILD';

/*引入的模块*/
var app = angular.module('myApp', [
    'ngRoute',  //路由
    'oc.lazyLoad',   //懒加载/动态加载
    'ngCookies'
]);


/**
 * Created by Ken on 2017/3/15.
 */
var routerName = ['login', 'busPrice', 'case', 'news', 'contact'];
/*app*/
/*路由*/
app.config(['$routeProvider',
    function ($routeProvider) {

        var loginResolve = {     //登录的resolve方法，不登录不允许访问其他路由
            auth: ['$q', 'ser_log', '$location', '$cookies',
                function ($q, ser_log, $location, $cookies) {
                    if ( ENV == 'BUILD' ){
                        if ($cookies.get('isLog') != 1) {   //有cookie就免登录,表示不关闭浏览器就不用登录
                            if (!ser_log.isLogin) {     //未登录跳到登录页
                                $location.path('/');    //跳转
                                return $q.reject('Not logged');
                            }
                        }
                    }
                }
            ]
        };

        var router = [];
        angular.forEach(routerName,function (item,index) {
            router.push(    //创建路由对象数组
                {
                    url: '/' + item,
                    option: {
                        'templateUrl': './pages/' + item + '.html',
                        'controller': item + 'Ctrl',
                        resolve: loginResolve
                    }
                }
            )
        });

        $routeProvider.when('/', {  //默认首页到登录页
            'templateUrl': './pages/' + routerName[0] + '.html',
            'controller': routerName[0] + 'Ctrl'
        })
            .when(router[1].url, router[1].option)
            .when(router[2].url, router[2].option)
            .when(router[3].url, router[3].option)
            .when(router[4].url, router[4].option)
    }]);
/**
 * Created by ken on 2017/4/21.
 */
/*  提示框服务
 * */
app.directive('myAlert', function() {
    return {
        restrict: 'E',
        template: '<div id="alert">{{alert}}</div>',
        replace: true,
    }
});

/**
 * Created by ken on 2017/4/21.
 */
/*  回弹按钮指令
 * */
app.directive('backButton', function() {
    return {
        restrict: 'E',
        template:   '<button id="back-button">' +
                        '<img src="./img/icon_top.png"/>' +
                    '</button>',
        replace: true,
        //功能
        compile: function (elem, attr) {
            elem.bind('click', function () {
                $('html,body').animate({scrollTop:0}, 300);
            });
            //窗口
            $(window).scroll(function() {
                var toTop = $(window).scrollTop();
                if( toTop > 50) {
                    elem.fadeIn(100);
                } else {
                    elem.fadeOut(200);
                }
            });
        }
    }
});

/**
 * Created by ken on 2017/4/23.
 */
/*  分页指令
*   对外暴露的有两个参数，一个是当前页page，一个是最大页数maxPage
*   对最大页数进行监听，如果改变了就更新最大页数数组
 * */
app.directive('pagination', function() {
    return {
        //元素
        restrict: 'E',
        //作用域
        scope:{
            page: '=',
            maxPage: '='
        },
        //html
        template:
            '<div class="pagination">' +
                '<ul class="pager">' +
                    '<li><a href="javascript:void(0)" ng-click="pageGo(1)">首页</a></li>' +
                    '<li><a href="javascript:void(0)" ng-click="pagePre()">上一页</a></li>' +
                '</ul>' +
                '<ul>' +
                    '<li ng-repeat="num in pageShowList" ng-class="{active: clickPage == num}">' +
                    '<a href="javascript:void(0)" ng-click="pageGo(num)">{{num}}</a>' +
                    '</li>' +
                '</ul>' +
                '<ul class="pager">' +
                    '<li><a href="javascript:void(0)" ng-click="pageNext()">下一页</a></li>' +
                    '<li><a href="javascript:void(0)" ng-click="pageGo(maxPage)">尾页:{{maxPage}}</a></li>' +
                '</ul>' +
            '</div>',
        //替换
        replace: true,
        //link函数
        link: function ($scope) {
            //变量
            var pageList = [];
            $scope.page = 1;    //初始默认为第一页
            $scope.pageShowList = [];    //最大显示8个格子

            /*  监听最大页数，如果页数变化，重新生成页数数组
            * */
            var watch = $scope.$watch('maxPage', function (newValue, oldValue, scope) {
                pageList = [];
                for (var i = 1; i <= newValue; i++) {
                    pageList.push(i);
                }
                resetPageOrder($scope.page);
            });

            /*  直接跳页
             * */
            $scope.pageGo = function (num) {
                $scope.page = num;
                resetPageOrder($scope.page);
            };

            /*  上一页
             * */
            $scope.pagePre = function () {
                if( $scope.page > 1){
                    $scope.page --;
                    resetPageOrder($scope.page);
                }
            };

            /*  下一页
             * */
            $scope.pageNext = function () {
                if ( $scope.page < $scope.maxPage ){
                    $scope.page ++;
                    resetPageOrder($scope.page);
                }
            };

            /*  重新设置页码
             * */
            function resetPageOrder(num) {
                $scope.clickPage = num; //变色
                if (num > 4 ) {
                    $scope.pageShowList = [
                        num - 3,
                        num - 2,
                        num - 1,
                        num,
                        num + 1,
                        num + 2,
                        num + 3
                    ];
                    if ( num > $scope.maxPage - 3 ){    //去除多出的页数
                        for ( var i = 0; i < 3-($scope.maxPage - num); i ++ ){
                            $scope.pageShowList.pop();
                        }
                    }
                } else{  //点击小于4的页数
                    $scope.pageShowList = pageList.slice(0, 7);  //只显示最大7个
                }
            }
        }
    }
});

/**
 * Created by Ken on 2017/3/15.
 */
/*公交车刊例价的控制器*/
app.controller('busPriceCtrl', [
    '$scope', '$http', '$cookies', 'ser_api', 'ser_strEncode', 'ser_log',
    function ($scope, $http, $cookies, ser_api, ser_strEncode, ser_log) {

        //---变量
        var isClickEditDel = false; //输入框显示，原来文本消失的条件
        //搜索关键字对象
        var keyBus = {
            page: 1,
            data: {
                id: '',
                province: '',
                city: '',
                route: '',
                level: '',
                type: '',
                time: '',
                price: ''
            }
        };
        $scope.page = 1;   //初始页码,保存当前页的页码
        $scope.clickPage = 1;
        $scope.list = [];   //数据列表
        $scope.keyword = ['', '', ''];  //关键字列表
        $scope.isDelBtnShow = [false, false, false];    //搜索处的删除按键

        /*-------------------------------- 初始化函数 ----------------------------------*/
        getData();

        /*-------------------------------- 事件绑定 ----------------------------------*/
        /*  转码：类型
         *   @param srt输入字符
         * */
        $scope.typeToCh = function (str) {
            return ser_strEncode.busPrice.type.decode(str);
        };

        /*  转码：类型
         *   @param srt输入字符
         * */
        $scope.levelToStr = function (str) {
            return ser_strEncode.busPrice.level.decode(str);
        };

        /*  转码：英文逗号都转为中文逗号
         * */
        $scope.myFilterToChComma = function (str) {
            var pattern = new RegExp("[, ]"); //特殊字符
            $scope.editItem.route = str.replace(pattern, '，');
        };

        /*  按键：表格修改
         *   @param index点击的行的序号
         * */
        $scope.editModify = function (index) {
            $scope.isShowTabEdit = index; //显示输入框
            $scope.editItem = $scope.list[index];   //表格值赋给输入框
            isClickEditDel = true;
        };

        /*  按键：表格保存
         *   @param index点击的行的序号
         * */
        $scope.editSave = function (index) {
            if (isClickEditDel) {  //点击过修改或者新建
                isClickEditDel = false;
                $scope.isShowTabEdit = null; //不显示输入框
            }
            var params = {  //上传的对象，json
                page: $scope.page,
                data: $scope.list[index]
            };
            $http.post(ser_api.busPrice.modifyData, params)
                .success(function (res) {
                    if (res == 100) {
                        getData();
                    }
                });
        };

        /*  按键：表格删除
         *   @param index点击的行的序号
         * */
        $scope.editDel = function (index) {
            $scope.isShowTabEdit = null; //不显示输入框
            $http({
                url: ser_api.busPrice.delData,
                method: 'GET',
                params: {
                    id: $scope.list[index].id,
                    page: $scope.page
                }
            }).success(function (res) {
                if (res == 100) {
                    getData();
                }
            });
        };

        /*  提交表单编辑
         *   @param index需要增加的行序号，会复制当前行
         * */
        $scope.editAdd = function (index) {
            var newList = $scope.list.slice(0);
            //var newItem = arr[ index ];   //复制的数组还是无法使用，只能用对象赋值
            var newItem =
                {
                    "id": 'id',
                    "province": newList[index].province,
                    "city": newList[index].city,
                    "level": newList[index].level,
                    "type": newList[index].type,
                    "time": newList[index].time,
                    "route": newList[index].route,
                    "price": newList[index].price
                };
            $scope.list.splice(index + 1, 0, newItem);    //插入
            $scope.editModify(index + 1); //设置为修改
        };
        /*  搜索按钮点击
         *   @param index搜索框的序号
         * */
        $scope.searchKeyWord = function (index) {
            //搜索后，删除按钮显示
            for (var i = 0; i < 3; i++) {
                if ($scope.keyword[i] != '') {
                    $scope.isDelBtnShow[i] = true;
                }
            }
            $scope.page = 1;
            search();
        };

        /*  取消搜索条件
         *@param index框的序号
         * */
        $scope.searchWithout = function (index) {
            $scope.isDelBtnShow[index] = false; //隐藏此按钮
            $scope.keyword[index] = '';
            search();
        };

        /*  搜索等级时可选框显示
         * */
        $scope.isShowPlaceWordList = false;
        $scope.searchLevelFocus = function () {
            $scope.isShowPlaceWordList = true;
        };

        /*  点击搜索，可选框消失
         * */
        $scope.searchLevelVal = function (val) {
            $scope.keyword[2] = val;
            $scope.isShowPlaceWordList = false;
        };

        /*-------------------------------- 待调用函数 ----------------------------------*/

        /*  获取全部数据
         * */
        function getAllData() {
            var url = ser_api.busPrice.getData;
            $http.get(url, {
                params: {
                    page: $scope.page   //根据当前页获取
                }
            }).success(function (res) {
                var list = res.data;
                list.forEach(function (item, index) {   //获得的列表的部分需要从number转换成str，以与select标签的值挂接
                    item.level += '';
                    item.type += '';
                    item.time += '';
                });
                $scope.list = list;
                $scope.list.unshift({   //加入一个新的空白框，方便新建
                    id: 'id'
                });
                $scope.maxPage = res.maxPage;
            });
        }

        /*  定义获取数据的函数
         * */
        function getData() {
            if (isSearchEmpty()) { //判断搜索框是否有内容
                getAllData();
            } else {
                search();   //搜索
            }
        }

        /*  搜索功能，从keyBus处获取关键词
         * */
        function search() {
            keyBus.page = $scope.page;
            keyBus.data.province = $scope.keyword[0];
            keyBus.data.city = $scope.keyword[1];
            keyBus.data.level = ser_strEncode.busPrice.level.encode($scope.keyword[2].toUpperCase());
            $http.post(ser_api.busPrice.searchData, keyBus).success(function (res) {
                res.results.forEach(function (item, index) {   //获得的列表的部分需要从number转换成str，以与select标签的值挂接
                    item.level += '';
                    item.type += '';
                    item.time += '';
                });
                $scope.list = res.results;
                $scope.maxPage = res.maxPage;
            });
        }

        /*  判断搜索框是否为空
         * */
        function isSearchEmpty() {
            var bool = true;
            for (var i = 0; i < 3; i++) {
                if ($scope.keyword[i] != '') {
                    bool = false;
                }
            }
            return bool;
        }

        $scope.pageTo = function () {
            getData();
        };
    }]);
/**
 * Created by Ken on 2017/3/15.
 */
/*主控制器*/
app.controller('caseCtrl', [
    '$scope', '$http', 'ser_umeditor', 'ser_api', 'ser_uploadFile',
    function ($scope, $http, ser_umeditor, ser_api, ser_uploadFile) {

        //变量
        var isClickEditDel = false; //输入框显示，原来文本消失的条件
        $scope.page = 1;
        var editingIndex = 0; //正在编辑的数据的序号，防止点击另外数据的保存按键
        $scope.list = [];   //清空列表
        var newItem = {
            id: 'id'
        };
        var allCaseDataArr = [];    //全部案例的信息数组
        var typeList = [
            {ch: '全部类别', eng: 'all'},
            {ch: '食品酒饮', eng: 'drink_wine'},
            {ch: '保健美妆', eng: 'healthy_pretty'},
            {ch: '家电日用', eng: 'appliance'},
            {ch: 'IT通讯', eng: 'IT'},
            {ch: '银行保险', eng: 'bank'},
            {ch: '家建地产', eng: 'land'},
            {ch: '服装服饰', eng: 'cloth'},
            {ch: '餐饮娱乐', eng: 'entertainment'},
            {ch: '教育培训', eng: 'education'},
            {ch: '公益广告', eng: 'public_service'}
        ];
        $scope.typeList = typeList;
        $scope.selectName = typeList[0].eng;

        /*-------------------------------- 初始化函数 ----------------------------------*/
        ser_umeditor.load();    //加载编辑器
        getData();

        /*-------------------------------- 事件绑定 ----------------------------------*/
        /*  类别搜索
         * */
        $scope.searchByType = function () {
            $scope.page = 1;
            getData();
        };

        /*  编辑：修改
         * */
        $scope.editModify = function (index) {
            $scope.isShowTabEdit = index; //显示输入框
            $scope.editItem = $scope.list[index];
            isClickEditDel = true;  //显示输入框
            editingIndex = index;
            var articleStr = $scope.list[index].content;    //拿到列表中的文章内容
            if (articleStr) {
                ser_umeditor.set(articleStr); //显示
            } else {
                ser_umeditor.set(''); //文章为空，清空
            }
        };

        /*  编辑：保存
         * */
        $scope.editSave = function (index) {
            if (editingIndex != index) {   //不是编辑中的数据的按键，返回
                return;
            }
            var imgUrlArr = []; //保存图片url
            hideInput();
            //图片收集
            var img = $('#myEditor').find('img');    //找出图片
            if (img.length > 0) {      //如果包含图片
                img.each(function () {
                    var urlStr = $(this).attr('src');   //收集url
                    var targetHost = '';
                    if (ENV == 'TEST') {
                        targetHost = ser_api.host;
                    } else if (ENV == 'BUILD') {
                        targetHost = 'http://' + window.location.host;
                    }
                    urlStr = urlStr.replace(targetHost, '');    //去除ip地址
                    imgUrlArr.push(urlStr);
                });
            }
            var params = {  //上传的对象，json
                page: $scope.page,
                data: {
                    id: $scope.list[index].id,
                    type: $scope.list[index].type,
                    title: $scope.list[index].title,
                    date: $scope.list[index].date,
                    logo: $scope.list[index].logo,
                    origin: $scope.list[index].origin,
                    content: ser_umeditor.get(),    //获取文章内容
                    img: imgUrlArr  //图片url数组
                }
            };
            $http.post(ser_api.case.add, params).success(function (res) {
                if (res == 100) {
                    getData()
                }
            });
        };

        /*  编辑：删除
         * */
        $scope.editDel = function (index) {
            var url = ser_api.case.delDate;
            $http.get(url, {
                params: {
                    page: $scope.page,   //根据当前页获取
                    delid: $scope.list[index].id
                }
            }).success(function (res) {
                if (res == 100) {
                    getData()
                }
            });
        };

        /*  logo图片上传
         * */
        $scope.uploadLogo = function(files, index){
            var url = ser_api.case.uploadImg;
            ser_uploadFile.postImg(url, files[0], 'upfile', function(data){
                var time = new Date().getTime();
                $scope.$apply(function () {
                    $scope.list[editingIndex].logo = ser_api.host + data + '?' + time;  //增加随机参数可强制刷新
                });
            });
        };

        /*  文章图片上传
         * */
        $scope.uploadFile = function (files) {
            var url = ser_api.case.uploadImg;
            ser_uploadFile.postImg(url, files[0], 'upfile', function(data){
                var res = ser_api.host + data;
                ser_umeditor.add('<img class=upload-img src=' + res + ' />');  //插入图片
            });
        };

        /*  类别转为中文
         * */
        $scope.typeToCh = function (str) {
            var strCh = str;
            angular.forEach(typeList, function (item,index) {
                if(str == item.eng){
                    strCh =  item.ch;
                }
            });
            return strCh;
        };

        /*  点击分页获取数据
        * */
        $scope.pageTo = function () {
            getData();
        };

        /*-------------------------------- 待调用函数 ----------------------------------*/
        /*  根据选择框内容，获取数据
         * */
        function getData() {
            //如果是‘全部’
            if ($scope.selectName == typeList[0].eng) {
                getAllData();
            }
            //否则搜索
            else {
                getSearchData();
            }
        }

        /*  定义获取后台案例数据的函数
         * */
        function getAllData() {
            var url = ser_api.case.getData;
            //var url = './data/mockCaseData.json';     //模拟的后台数据
            $http.get(url, {
                params: {
                    page: $scope.page   //根据当前页获取
                }
            }).success(function (res) {
                $scope.list = res.consults;
                $scope.list.unshift( newItem ); //加入一个新的空白框，方便新建
                $scope.maxPage = res.maxPage;
            });
        }

        /*  定义搜索函数
        * */
        function getSearchData(){
            var url = ser_api.case.search;
            var params = {
                page: $scope.page,   //根据当前页获取
                type: $scope.selectName
            };
            $http.post(url, params).success(function (res) {
                $scope.list = res.consults;
                $scope.list.unshift( newItem ); //加入一个新的空白框，方便新建
                $scope.maxPage = res.maxPage;
            });
        }

        /*  隐藏修改用的输入框
         * */
        function hideInput() {
            if (isClickEditDel) {  //点击过修改或者新建
                isClickEditDel = false;
                $scope.isShowTabEdit = null; //不显示输入框
            }
        }
    }]);



/**
 * Created by ken on 2017/4/9.
 */
/*主控制器*/
app.controller('contactCtrl', [
    '$scope', '$http', 'ser_api',
    function ($scope, $http, ser_api ) {
        //变量
        var isClickEditDel = false; //输入框显示，原来文本消失的条件
        $scope.list = [];   //清空列表
        $scope.page = 1;

        /*-------------------------------- 初始化函数 ----------------------------------*/
        getData();

        /*-------------------------------- 事件绑定 ----------------------------------*/
        /*  未读状态转换成文字，未读的加上背景色
         *   @param str未读状态
         * */
        $scope.boolToStr = function( bool, index ){
            if ( bool == true ){
                return '';
            }else if( bool == false ){
                $scope.isContactUnread = index;
                return '未读';
            }
        };

        /*  标记为已读
         *   @param index在表中的序号
         * */
        $scope.editModify = function ( index ) {
            var params = {
                page: $scope.page,
                id: $scope.list[index].id
            };
            var url = ser_api.contact.readData;
            $http.post( url, params).success(function (res) {
                if ( res == 100 ){
                    getData();
                }
            });
        };

        /*  删除
         *   @param index在表中的序号
         * */
        $scope.editDel = function( index ){
            var url = ser_api.contact.delData;
            $http.get( url, {
                params: {
                    page: $scope.page,
                    delid: $scope.list[index].id
                }
            }).success(function (res) {
                if ( res == 100 ){
                    getData();
                }
            });
        };

        /*  点击分页
        * */
        $scope.pageTo = function () {
            getData();
        };

        /*-------------------------------- 待调用函数 ----------------------------------*/
        /*  定义获取数据的函数
         * */
        function getData() {
            var url = ser_api.contact.getData;
            $http.get(url, {
                params: {
                    page: $scope.page   //根据当前页获取
                }
            }).success(function (res) {
                $scope.list = res.customers;
                $scope.maxPage = res.maxPage
            });
        }


    }]);



/**
 * Created by Ken on 2017/3/15.
 */
/*主控制器*/
app.controller('indexCtrl', [
    '$scope', '$http', '$cookies', 'ser_api', 'ser_log',
    function ($scope, $http, $cookies, ser_api, ser_log) {

        //变量初始化
        $scope.isShowContactNum = false;
        $scope.contactNum = 0;  //获取到的通知的信息数量
        $scope.isNavLiAct = null;

        /*-------------------------------- 事件绑定 ----------------------------------*/

        /*  导航点击事件
         * */
        $scope.sleTab = function (idx) {
            $scope.isNavLiAct = idx;    //当前路由背景变色
            if (idx == 3) {    //第三个是客户联系，取消泡泡
                $scope.isShowContactNum = false;
            }
        };


        /*  哈希改变都执行一次
         * */
        window.onhashchange = function () {
            selTabByHash();
        };

        /*-------------------------------- 初始化函数 ----------------------------------*/
        selTabByHash();

        /*-------------------------------- 待调用函数 ----------------------------------*/

        /*  根据hash改变tab颜色
         * */
        function selTabByHash() {
            var hash = window.location.hash;  //要判断是否含有hash
            var hashName = hash.split('#/')[1];
            for (var i = 1; i < routerName.length; i++) {
                if (routerName[i] == hashName) {   //名字一致
                    $scope.isNavLiAct = i-1;
                    break;
                }
            }
        }

        //  获得未读客户信息数量
        var url = ser_api.contact.getUnreadNum;
        $http.get(url).success(function (res) {
            if (res > 0) {
                $scope.isShowContactNum = true;
                $scope.contactNum = res;
            }
        });
    }]);
/**
 * Created by Ken on 2017/3/15.
 */
/*主控制器*/
app.controller('loginCtrl', [
    '$scope', '$http', '$location', '$cookies', 'ser_api', 'ser_log',
    function ($scope, $http, $location, $cookies, ser_api, ser_log) {

        //变量初始化
        $scope.username = '';
        $scope.password = '';

        /*-------------------------------- 初始化函数 ----------------------------------*/

        /*-------------------------------- 事件绑定 ----------------------------------*/

        /*  键盘在登录界面上事件，回车登录
         * */
        $scope.loginKeyDown = function ($event) {
            if ($event.keyCode == 13) {
                sendLogInfo();
            }
        };

        /*  登录按键
         * */
        $scope.logBtnClick = function () {
            sendLogInfo();
        };

        /*-------------------------------- 待调用函数 ----------------------------------*/
        /*  发送登录信息
         * */
        function sendLogInfo() {
            var params = {
                username: $scope.username,
                password: md5($scope.password)    //加密
            };
            if (ENV == 'TEST') {
                loginSuccess();
            } else if(ENV == 'BUILD'){
                $http.post(ser_api.admin.login, params)
                    .success(function (res) {
                        if (res == 100) {  //登录成功
                            $cookies.put('isLog', 1);    //cookie记录，一天之内不用登录
                            loginSuccess();
                        }
                    });
            }
        }

        /*  登录函数
         * */
        function loginSuccess() {
            ser_log.isLogin = true;     //全局通知
            $scope.loginKeyDown = function () {     //取消按键事件
                return false;
            };
            (ser_log.getPath());
            $location.path('/busPrice');    //默认登录跳转刊例价页
        }
    }]);
/**
 * Created by ken on 2017/4/7.
 */
/**
 * Created by Ken on 2017/3/15.
 */
/*主控制器*/
app.controller('newsCtrl', [
    '$scope', '$http', 'ser_umeditor', 'ser_api', 'ser_strEncode', 'ser_uploadFile',
    function ($scope, $http, ser_umeditor, ser_api, ser_strEncode, ser_uploadFile ) {

        //变量
        var isClickEditDel = false; //输入框显示，原来文本消失的条件
        var editingIndex = 0;
        $scope.page = 1;
        $scope.list = [];   //清空列表
        var newItem = {     //新建文章默认属性
            id: 'id',
            title: '',
            origin: '',
            type: 1,
            date: '',
            content: '',
            txt: '' //纯文本，用于做摘要
        };

        /*-------------------------------- 初始化函数 ----------------------------------*/
        ser_umeditor.load();    //加载编辑器
        getData();

        /*-------------------------------- 事件绑定 ----------------------------------*/
        /*  转码：类型
         *   @param srt输入字符
         * */
        $scope.typeToCh = function (str) {
            return ser_strEncode.news.type.encode( str );
        };

        /*  添上序号
         * */
        $scope.indexItem = function ( index ) {
            var num = 0;
            if ( index > 0 ){
                num = ($scope.page-1) * 10 + index;
            }else{
                num = 'new';
            }
            return num;
        };

        /*  编辑：修改
         * */
        $scope.editModify = function (index) {
            $scope.isShowTabEdit = index; //显示输入框
            $scope.editItem = $scope.list[index];
            isClickEditDel = true;
            editingIndex = index;   //当前编辑序号保存
            var articleStr = $scope.list[index].content;    //拿到列表中的文章内容
            if ( articleStr ){
                ser_umeditor.set(articleStr); //显示
            }else{
                ser_umeditor.set(''); //文章为空，清空
            }
        };

        /*  编辑：保存
         * */
        $scope.editSave = function (index) {
            if ( editingIndex != index ){   //不是编辑中的数据的按键，返回
                return;
            }
            var imgUrlArr = []; //保存图片url
            //图片收集
            if (isClickEditDel) {  //点击过修改或者新建
                isClickEditDel = false;
                $scope.isShowTabEdit = null; //不显示输入框
            }
            var img = $('#myEditor').find('img');    //找出图片
            var targetHost = '';    //需要清除ip地址
            if (img.length > 0) {      //如果包含图片
                img.each(function () {
                    var urlStr = $(this).attr('src');   //收集url
                    if ( ENV == 'TEST'){
                        targetHost = ser_api.host;
                    }else if(ENV == 'BUILD'){
                        targetHost = 'http://' + window.location.host;
                    }
                    urlStr = urlStr.replace(targetHost, '');    //去除ip地址
                    imgUrlArr.push(urlStr);
                });
            }
            //去除文章中所有图片的ip地址
            var upLoadArticle = ser_umeditor.get();
            if( ENV == 'BUILD'){
                var newRegExp = new RegExp(targetHost, 'gm');   //全局替换正则
                upLoadArticle = upLoadArticle.replace(newRegExp,'')
            }
            var params = {  //上传的对象，json
                page: $scope.page,
                data: {
                    id: $scope.list[index].id,
                    type: $scope.list[index].type,
                    title: $scope.list[index].title,
                    origin: $scope.list[index].origin,
                    date: $scope.list[index].date,
                    txt: ser_umeditor.getTxt(),     //纯文本内容
                    content: upLoadArticle,    //获取文章内容
                    img: imgUrlArr  //图片url数组
                }
            };
            $http.post(ser_api.news.add, params).success(function (res) {
                if (res == 100) {
                    getData()
                }
            });
        };

        /*  编辑：删除
         * */
        $scope.editDel = function (index) {
            var url = ser_api.news.delData;
            $http.get(url, {
                params: {
                    page: $scope.page,   //根据当前页获取
                    delid: $scope.list[index].id
                }
            }).success(function (res) {
                if (res == 100) {
                    getData()
                }
            });
        };

        /*  文章图片上传
         *   @param files图片文件
         * */
        $scope.uploadFile = function (files) {
            var url = ser_api.case.uploadImg;
            ser_uploadFile.postImg(url, files[0], 'upfile', function(data){
                var res = ser_api.host + data;
                ser_umeditor.add('<img class=upload-img src=' + res + ' />');  //插入图片
            });
        };

        /*  点击分页
         * */
        $scope.pageTo = function () {
            getData();
        };

        /*-------------------------------- 待调用函数 ----------------------------------*/
        /*  定义获取数据的函数
         * */
        function getData() {
            var url = ser_api.news.getData;
            $http.get(url, {
                params: {
                    page: $scope.page   //根据当前页获取
                }
            }).success(function (res) {
                var data = res.consults;
                data.forEach(function (item, index) {
                    item.type = item.type.toString();   //后台原因，需要把type转成字符串
                });
                data.unshift( newItem ); //加入一个新的空白框，方便新建
                $scope.list = data;
                $scope.maxPage = res.maxPage;
            });
        }

        /*  隐藏修改用的输入框
         * */
        function hideInput(){
            if (isClickEditDel) {  //点击过修改或者新建
                isClickEditDel = false;
                $scope.isShowTabEdit = null; //不显示输入框
            }
        }
    }]);



/**
 * Created by ken on 2017/3/20.
 */
/*  url服务生产
* */
app.factory('ser_api', function(){
    var host = '';
    if ( ENV == 'TEST' ){
        host = 'http://192.168.18.23:8080';
    }else if(ENV == 'BUILD'){
        host = '';
    }

    return {
        //ip地址
        host: host,

        // 公交车刊例价
        busPrice: {
            //获取公交车列表 get
            getData: host + '/xgjzx/bus/find',

            //修改数据 post
            modifyData: host + '/xgjzx/bus/addupd',

            //删除数据 post
            delData: host + '/xgjzx/bus/delete',

            //搜索数据 post
            searchData: host + '/xgjzx/search/found'
        },

        //用户登录 post
        admin: {
            login: host + '/xgjzx/login/backsign'
        },

        //案例展示
        case: {
            //获取文章列表 get
            getData: host + '/xgjzx/consult/select',

            //图片上传 post
            uploadImg: host + '/xgjzx/consult/uploadimage',

            //文章增加 post
            add: host + '/xgjzx/consult/add',

            //删除 post
            delDate: host + '/xgjzx/consult/delete',

            //类型
            search: host + '/xgjzx/consult/search'
        },

        //新闻
        news: {
            //获取文章列表 get
            getData: host + '/xgjzx/news/select',

            //图片上传 post
            uploadImg: host + '/xgjzx/news/uploadimage',

            //文章增加 post
            add: host + '/xgjzx/news/add',

            //删除 post
            delData: host + '/xgjzx/news/delete'
        },

        //客户联系
        contact: {
            //增加联系方式 post
            add: host + '/xgjzx/customer/add',

            //获取全部列表 get
            getData: host + '/xgjzx/customer/select',

            //获取未读列表 get
            getUnreadData: host + '/xgjzx/customer/newcustomer',

            //获取已读列表 get
            getReadData: host + '/xgjzx/customer/oldcustomer',

            //获取未读数量 get
            getUnreadNum: host + '/xgjzx/customer/countnum',

            //删除 get
            delData: host + '/xgjzx/customer/delete',

            //标记为已读 post
            readData: host + '/xgjzx/customer/updatestatus'
        }
    };
});


/**
 * Created by ken on 2017/3/24.
 */
/*  字符串转换服务
* */
app.factory('ser_log', [
    '$location',
    function($location){
    //全局变量：是否登录
    var isLogin = false;
    var location = $location.path();

    return {
        isLogin: isLogin,

        /*  保存刷新之前的路径
        * */
        savePath: function () {
            location = $location.path();
        },

        /*  返回保存的路径
        * */
        getPath: function () {
            return location;
        }
    }
}]);
/**
 * Created by ken on 2017/3/24.
 */
/*  字符串转换服务
* */
app.factory('ser_strEncode', function(){

    return{
        busPrice:{
            type: {
                decode: function ( str ) {
                    switch ( str ) {
                        case '1':return '全车';
                        case '2':return '半车';
                        case '3':return '海报';
                        default: return '';
                    }
                },
                encode: function ( str ) {
                    switch ( str ) {
                        case '全车':return '1';
                        case '半车':return '2';
                        case '海报':return '3';
                        default: return '';
                    }
                }
            },
            level: {
                encode: function ( str ) {
                    switch( str ) {
                        case 'S':   return '1';
                        case 'A++': return '2';
                        case 'A+':  return '3';
                        case 'A':   return '4';
                        case 'B':   return '5';
                        case 'C':   return '6';
                        case 'D':   return '7';
                        default: return '';
                    }
                },
                decode: function ( str ) {
                        switch (str) {
                            case '1':return 'S';
                            case '2':return 'A++';
                            case '3':return 'A+';
                            case '4':return 'A';
                            case '5':return 'B';
                            case '6':return 'C';
                            case '7':return 'D';
                            default: return '';
                        }
                    }
                }
            },
        news: {
            type: {
                encode: function ( str ) {
                    switch( str ) {
                        case '1': return '资信快报';
                        case '2': return '业界活动';
                        case '3': return '行业动态';
                        case '4': return '招标信息';
                        default: return '';
                    }
                },
                decode: function ( str ) {
                    switch( str ) {
                        case '行业动态': return '1';
                        case '业界活动': return '2';
                        case '行业动态': return '3';
                        case '招标信息': return '4';
                        default: return '';
                    }
                }
            }
        }
    }
});
/**
 * Created by ken on 2017/4/6.
 */
/**
 * Created by ken on 2017/3/24.
 */
/*  umeditor服务
 * */
app.factory('ser_umeditor', [
    '$ocLazyLoad',
    function( $ocLazyLoad ){
        //编辑器变量
        var um;

        return {
            /*  加载umeditor，需要重新加载文件，所以用到lazyload
             * */
            load: function () {
                $ocLazyLoad.load([
                    {
                        files: ['./js/lib/umeditor/umeditor.min.js'],
                        cache: false    //不缓存，里面有编辑器初始化的代码
                    }
                ]).then(function () {
                    //自定义工具栏，去掉了图片上传
                    window.UMEDITOR_CONFIG.toolbar = [
                        'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
                        'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize',
                        '| justifyleft justifycenter justifyright justifyjustify |',
                        'link unlink | emotion video  | map',
                        '| horizontal print preview fullscreen', 'drafts', 'formula'
                    ];
                    um = UM.getEditor('myEditor');  //实例化
                });
            },

            /*  获取内容
            * */
            get: function () {
                return um.getContent();
            },

            /*  获取纯文本
             * */
            getTxt: function () {
                return um.getContentTxt();
            },

            /*  追加内容
            * */
            add: function ( str ) {
                um.setContent( str, true );
            },

            /*  设置内容
            * */
            set: function ( str ) {
                um.setContent( str, false );
            }
        };
}]);
/**
 * Created by ken on 2017/4/27.
 */
app.factory('ser_uploadFile',
    function () {
        return {
            /*  图片post上传
             * */
            postImg: function (url, file, name, fn) {
                var formData = new FormData();
                formData.append(name, file);    //拿文件列表的第一个
                //http上传
                var xhr = new XMLHttpRequest();
                xhr.open('post', url);
                xhr.send(formData);
                xhr.onreadystatechange = function () {
                    if (this.readyState == 4 && this.status == 200) {
                        var data = this.response;
                        fn && fn(data);
                    }
                };
            }
        }
    }
);

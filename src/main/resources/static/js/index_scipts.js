var app=angular.module("bookstore",['ngRoute']);

function add(book) {
    $.post("/addcart",{
        "id":book.id
    },function () {
        alert("成功加入购物车");
    });
}

function buy(book,count) {
    $.post("makeorder",{
        "id":book.id,
        "count":count
    },function(data){
        if (data) alert("下单成功");
        else alert("库存不足");
    });

}


app.config(['$routeProvider','$locationProvider',function ($routeProvider, $locationProvider) {
    $routeProvider
        .when('/',{
            controller:"IndexController",
            templateUrl:"index0.html"
        })
        .when('/add',{
            controller:"AddController",
            templateUrl:"add.html"
        })
        .when('/view/:id',{
            controller:"DetailController",
            templateUrl:"detail.html"
        })
        .when('/edit/:id',{
            controller:'EditController',
            templateUrl:"edit.html"
        })
        .when('/cart',{
            controller:"CartController",
            templateUrl:"cart.html"
        })
        .when('/user',{
            controller:"UserController",
            templateUrl:"user.html"
        })
        .when('/order',{
            controller:"OrderController",
            templateUrl:"order.html"
        })
        .when('/user_edit',{
            controller:"UserEditController",
            templateUrl:"user_edit.html"
        })
        .when('/userEdit/:id',{
            controller:"UserEditIdController",
            templateUrl:"user_editid.html"
        })
        .when('/management',{
            controller:"ManagementController",
            templateUrl:"management.html"
        })
        .when('/statistics',{
            controller:"StatisticsController",
            templateUrl:"statistics.html"
        })
        .when('/addUser',{
            controller:"AddUserController",
            templateUrl:"addUser.html"
        })
        .when('/userEdit/:id',{
            controller:"UserEditIdController",
            templateUrl:"user_editid.html"
        })
        .otherwise({redirectTo:"/"});
}]);

app.controller("UserEditIdController",["$scope","$routeParams","$location",function ($scope, $routeParams, $location) {
    $.post("/getUserById",{
        "id":$routeParams.id
    },function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.user=data;
        });
    });
    $scope.save=function () {
        $.post("/updateUserById",{
            "id":$scope.user.id,
            "password":$scope.user.password,
            "email":$scope.user.email,
            "description":$scope.user.description,
            "phone":$scope.user.phone
        },function (data) {
            alert("修改成功");
            $location.path("/");
        });
    };
    $scope.cancel=function () {
        $location.path("/");
    };

}]);

app.controller("AddUserController",["$scope",function ($scope) {
}]);

app.controller("StatisticsController",["$scope",function ($scope) {
    $scope.search=function(query){
        $.post("statistics",{
            "query":query
        },function (data) {
            $scope.$apply(function () {
                $scope.order=data;
            })
        })
    };
}]);

app.controller("ManagementController",["$scope",function ($scope) {
    $.post("/getUsers",null,function (data) {
        $scope.$apply(function () {
            $scope.users=data;
        });
    });
    $scope.remove=function (id) {
        $.post("removeUser",{
            "id":id
        },function (data) {
            alert("成功删除用户");
            window.location.reload(true);
        });
    };
}]);

app.controller("MainController",['$route','$location','$routeParams',
        function ($route, $location, $routeParams) {
            this.$route=$route;
            this.$location=$location;
            this.$routeParams=$routeParams;
}]);

app.controller("IndexController",["$scope","$routeParams","$rootScope",function ($scope,$routeParams,$rootScope) {
    $.post("/isadmin",null,function(data){
        $rootScope.$apply(function () {
            $rootScope.isadmin=data;
        })
    });
    $.post("/books",null,function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.booklist=data;
        });
    });
    $scope.search=function (query) {
        $.post("/search",{
            "query":query
        },function (data) {
            $scope.$apply(function () {
                $scope.booklist=data;
            })
        });
    };
    $scope.add=add;
    $scope.buy=buy;
    $scope.remove=function(book){
        $.post("/removebook",{
            "id":book.id
        },function(){
            alert("删除成功！");
            window.location.reload(true);
        });
        window.location.reload(true);
    };
}]);

app.controller("AddController",[
    '$scope','$location',function ($scope,$location) {
        $scope.cancel=function () {
            $location.path("/");
        };
        $scope.add=function () {
            $.ajax({
                url: '/addbook',
                type: 'POST',
                cache: false,
                data: new FormData($('#addBookForm')[0]),
                processData: false,
                contentType: false
            }).done(function(res) {
                alert("成功添加书籍");
                window.location.reload(true);
            }).fail(function(res) {});
        };
    }
]);

app.controller("EditController",["$scope",'$routeParams','$location',function ($scope, $routeParams, $location) {
    $.post("/bookdetail",{
        "id":$routeParams.id
    },function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.book=data;
        });
    });
    $scope.save=function () {
        //books[$routeParams.id-1]=$scope.book;
        //提交到服务端
        $.ajax({
            url: '/updatebook',
            type: 'POST',
            cache: false,
            data: new FormData($('#editBookForm')[0]),
            processData: false,
            contentType: false
        }).done(function(res) {
            alert("成功修改书籍");
            window.location.reload(true);
        }).fail(function(res) {});
        $location.path("/");
    };
    $scope.cancel=function () {
        $location.path("/");
    };
}]);

app.controller("DetailController",['$scope','$routeParams',function ($scope,$routeParams) {
    $.post("/bookdetail",{
        "id":$routeParams.id
    },function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.book=data;
        });
    });
    $scope.add=add;
    $scope.buy=buy;
}]);

app.controller("CartController",["$scope",function ($scope) {
    $.post("/showcart",null,function (data) {
        $scope.$apply(function () {
            $scope.cart=data;
        })
    });
    $scope.remove=
    function remove(book){
        $.post("/removecart",{
            "id":book.id
        },function () {
            alert("已从购物车移除");
        });
        $.post("/showcart",null,function (data) {
            $scope.$apply(function () {
                $scope.cart=data;
            })
        });
    };
    $scope.buy=
    function buy(book,count) {
        $.post("makeorder",{
            "id":book.id,
            "count":count
        },function(data){
            if (data) alert("下单成功");
            else alert("库存不足");
        });
        $.post("/removecart",{
            "id":book.id
        },null
        );
        $.post("/showcart",null,function (data) {
            $scope.$apply(function () {
                $scope.cart=data;
            })
        });

    };
    $scope.buyall=function(){

    }

}]);

app.controller("UserController",["$scope",function ($scope) {
    $.post("/userdetail",null,function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.user=data;
        });
    });
}]);

app.controller("OrderController",["$scope",function ($scope) {
    $.post("/showorder",null,function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.order=data;
        });
    });
}]);

app.controller("UserEditController",["$scope","$location",function ($scope,$location) {
    $.post("/userdetail",null,function (data, statusText) {
        console.log(data);
        $scope.$apply(function () {
            $scope.user=data;
        });
    });
    $scope.save=function () {
        $.ajax({
            url: '/updateuser',
            type: 'POST',
            cache: false,
            data: new FormData($('#editUserForm')[0]),
            processData: false,
            contentType: false
        }).done(function(res) {
            alert("成功修改信息");
            $location.path("/");
        }).fail(function(res) {});
        $location.path("/");
    };
    $scope.cancel=function () {
        $location.path("/");
    };
}]);

jQuery(document).ready(function ($) {
    $('ul.nav > li').click(function (e) {
      $('ul.nav > li').removeClass('active');
      $(this).addClass('active');
   });
    var show_per_page=12;
    var current_page=0;
    var total_page=Math.ceil($(".mainBooks").children().length/show_per_page);

    $(".mainBooks").children().css("display","none");
    $(".mainBooks").children().slice(show_per_page*current_page,show_per_page).css("display","block");
    $(".previous").click(function(){
        if(current_page==0) return;
        else {
            --current_page;
            var start=current_page*show_per_page;
            var end=start+show_per_page;
            $(".mainBooks").children().css("display","none").slice(start,end).fadeIn("slow");
        }
    });
    $(".next").click(function(){
        if(current_page==total_page-1) return;
        else {
            ++current_page;
            var start=current_page*show_per_page;
            var end=start+show_per_page;
            $(".mainBooks").children().css("display","none").slice(start,end).fadeIn("slow");
        }
    });
});
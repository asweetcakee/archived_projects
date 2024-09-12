//Animate css
new WOW().init();

//Jquery script for scrolling, interacts with nav bar
$(document).ready(function() {
    $("#my-menu").on("click","a", function (event) {
        event.preventDefault();
        var id  = $(this).attr('href'),
            top = $(id).offset().top;
        $('body,html').animate({scrollTop: top}, 1000);
    });
});

var windowWidth = $(window).width();

if(windowWidth > 1200) {
    $(function() {
        $(".panel").css({
            "height":$(window).height()
        });
        $.scrollify({
            section:".panel", // селектор для секций (разделов) на странице
            interstitialSection : ".footer", // исключения для прокрутки
            interstitialSection : ".site-header",
            scrollSpeed: 1100,
            offset : 0, // расстояние в пикселях для комппенсации положения каждого раздела.
            scrollbars: true //Будет ли видна полоса прокрутки
        });
    });
}

if(windowWidth < 769){
    
    //Plugin mmenu
    jQuery(document).ready(function( $ ) {
        
        $('#my-menu').mmenu({
            extensions: ['theme-black', 'pagedim-black', 'position-right'],
            navbar: {
                title: 'Menu'
            }
        });

        // Добавление бургер меню
        var apiMmenu = $('#my-menu').data('mmenu');
        apiMmenu.bind('open:finish', function(){
            $('.hamburger').addClass('is-active');
        }).bind('close:finish', function(){
            $('.hamburger').removeClass('is-active'); 
        });

     });
}

$(window).scroll(function() {                              // отслеживаем событие
    if ( $(window).scrollTop() >= 60 ){                   // ставим условие
       $('.top-line').css('position', 'fixed');         // определяем действие
       $('.top-line').css('z-index', '999');
       $('.top-line').css('background-color', '#000');
       $('.top-line').css('border-bottom', '1.5px ridge #fff');
       $('.top-line').css('transition', 'ease-out 0.6s');
       
       if(windowWidth <= 768){
            $('.icon').css('margin-top','0px');   
       }
    }
    else if($(window).scrollTop() <= 150 ){
        $('.top-line').css('position', 'absolute');         // определяем действие
        $('.top-line').css('background', 'none');
        $('.top-line').css('border-bottom', 'none');
        if(windowWidth <= 768){
            $('.icon').css('margin-top', '1.3%');   
        }
    }
});
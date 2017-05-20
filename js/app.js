//'use strict';

var SPLASH_CLASS = 'splashBody';
var GAME_CLASS = 'gameBody';
var HIDE_CLASS = 'divHide';

var TABLE_CLASS = 'table';
var HIMA_CLASS = 'hima';
var TSUBU_CLASS = 'tsubushi';

var PANNEL_WIDTH_COUNT = 3;

var $splash = $('#splash');
var $gamePannel = $('#gamePannel');

var $appStartBtn = $('.js-appStart');
var $gameStartBtn = $('.js-gameStart');

var $himaBtn = $('.js-pannel');
var idname =  '';
var countHima = 0;

var vibe = {
	init: function(){
		navigator.vibrate(0);
	},
	act: function(arg) {
		navigator.vibrate(arg);
	}
};

function initGameWindow($target) {
	var wrapHeight = window.innerHeight;
	var wrapWidth = window.innerWidth;

	$target.css({
								height: wrapHeight,
								width: wrapWidth
							});
}

function setDivSize($target, h, w) {
	$target.css({
								height: h,
								width: w
							});
}


function initPannel() {
	var wrapHeight = window.innerHeight / PANNEL_COUNT;
	var wrapWidth = window.innerWidth / PANNEL_COUNT;
	$('.td').hasClass(TABLE_CLASS).css('height', wrapHeight).css('width', wrapWidth);
}

$(document).ready(function (){
	initGameWindow($splash);
	$gamePannel.removeClass(TABLE_CLASS);
	$gamePannel.addClass(HIDE_CLASS);

	initGameWindow($gamePannel);
	setTimeout(
		function (){
			$splash.removeClass(SPLASH_CLASS);
			$splash.removeClass(TABLE_CLASS);
			$splash.css({
										height: 0,
										width: 0
									});
			$gamePannel.addClass(TABLE_CLASS);
			$gamePannel.removeClass(HIDE_CLASS);
		},3000
	);
});

function checkPanel() {
	//フィルター掛けてない場合→全てタップした場合
	countHima = $('.' + HIMA_CLASS).length;
	if (countHima === 0) {
		return true;
	}
	return false;
}

$himaBtn.on('click', function() {
	//step.1
	//タップしたら画像が変わる暇⇒潰される
	idname = $(this).attr('id');
	if ($('#' + idname).hasClass(HIMA_CLASS)) {
		$('#' + idname).removeClass(HIMA_CLASS).addClass(TSUBU_CLASS);
	} else {
		return;
	}

	vibe.init();
	vibe.act(100);

	if (checkPanel()) {
		$splash.addClass(TABLE_CLASS);
		$splash.addClass(GAME_CLASS);
		$gamePannel.addClass(HIDE_CLASS);
		initGameWindow($splash);
		setDivSize($gamePannel, 0, 0);
		setTimeout(
			function (){
				//		$himaBtn.removeClass(TSUBU_CLASS).addClass(HIMA_CLASS);
			},3000);
//		$himaBtn.removeClass(TSUBU_CLASS).addClass(HIMA_CLASS);
	}
});

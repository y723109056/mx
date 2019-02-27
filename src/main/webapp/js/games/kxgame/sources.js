
var background = "/img/games/kxgame/background.png";
var image = ["/img/games/kxgame/tile_0.png","/img/games/kxgame/tile_1.png","/img/games/kxgame/tile_2.png","/img/games/kxgame/tile_3.png","/img/games/kxgame/tile_4.png","/img/games/kxgame/tile_5.png","/img/games/kxgame/floor_0.png","/img/games/kxgame/floor_1.png","/img/games/kxgame/floor_2.png","/img/games/kxgame/hidden.png","/img/games/kxgame/bomb.png","/img/games/kxgame/lock_0.png","/img/games/kxgame/lock_1.png","/img/games/kxgame/bomb_0.png","/img/games/kxgame/bomb_1.png","/img/games/kxgame/bomb_2.png","/img/games/kxgame/bomb_3.png","/img/games/kxgame/bomb_4.png","/img/games/kxgame/bomb_5.png","/img/games/kxgame/crump_0.png","/img/games/kxgame/crump_1.png","/img/games/kxgame/crump_2.png","/img/games/kxgame/crump_3.png","/img/games/kxgame/crump_4.png","/img/games/kxgame/crump_5.png","/img/games/kxgame/time_0.png","/img/games/kxgame/time_1.png","/img/games/kxgame/time_2.png","/img/games/kxgame/time_3.png","/img/games/kxgame/time_4.png","/img/games/kxgame/time_5.png","/img/games/kxgame/flash_0.png","/img/games/kxgame/flash_1.png","/img/games/kxgame/flash_2.png","/img/games/kxgame/flash_3.png","/img/games/kxgame/flash_4.png","/img/games/kxgame/flash_5.png","/img/games/kxgame/convert_portait.png","/img/games/kxgame/convert_landscape.png","/img/games/kxgame/flash.png","/img/games/kxgame/button.png","/img/games/kxgame/failed.png","/img/games/kxgame/succeed.png","/img/games/kxgame/infotip.png","/img/games/kxgame/menu.png","/img/games/kxgame/title.png","/img/games/kxgame/button.png","/img/games/kxgame/operate.png","/img/games/kxgame/pause.png"];
var audio = "/sounds/games/kxgame/SeaTreasureMatch.mp3";
var level = [{
				time: 300,
				map: [
					[, , , , , , , , ],
					[, , , 0, 0, 0, , , ],
					[, , 0, 0, 0, 0, 0, , ],
					[, 0, 0, 1, 0, 1, 0, 0],
					[, 0, 1, 0, 1, 0, 1, 0],
					[, 0, 1, 1, 0, 1, 1, 0],
					[, , 0, 0, , 0, 0, , ]
				]
			}, {
				time: 300,
				map: [
					[, , , , , , , , ],
					[, , 0, 0, 0, 0, 0, , ],
					[, 0, 0, 1, 1, 1, 0, 0],
					[, 0, 0, 1, , 1, 0, 0],
					[, 0, 0, 1, 1, 1, 0, 0],
					[, , 1, 1, 0, 1, 1, , ],
					[, 0, 0, 0, 0, 0, 0, 0]
				]
			}, {
				time: 300,
				map: [
					[, 0, 0, 0, 0, 0, 0, 0],
					[, , 0, 0, 1, 0, 0, , ],
					[, , , 1, 1, 1, , , ],
					[, , , , 4, , , , ],
					[, , , 0, 0, 0, , , ],
					[, , 0, 0, 1, 0, 0, , ],
					[, 0, 1, 1, 1, 1, 1, 0],
					[0, 0, 0, 1, 1, 1, 0, 0, 0]
				]
			}, {
				time: 300,
				map: [
					[, 0, 0, 0, 0, 0, 0, 0],
					[, , 0, 0, 0, 0, 0, 0],
					[, 0, 1, 0, , 1, 1, 0],
					[, 0, 1, , 0, 0, 1, 0],
					[, 0, 1, 0, 0, , 1, 0],
					[, 0, 1, 1, , 0, 1, 0],
					[, 0, 0, 0, 0, 0, 0, , ]
				]
			}, {
				time: 300,
				map: [
					[0, 1, 0, 0, 0, 0, 0, 1, 1],
					[0, 1, 0, 0, 0, 0, 1, 1, 0],
					[, 0, 0, 0, 0, 1, 1, 0, 0],
					[, , 0, 0, 1, 1, 0, 0, 0],
					[, , , 1, 1, 4, 4, 4, 4],
					[, , , , 0, 0, 0, 0, 0],
					[, , , , , 0, 0, 1, 1],
					[, , , , , , 0, 0, 0]
				]
			}, {
				time: 300,
				map: [
					[, 0, 0, 0, , 0, 0, 0],
					[, 0, 0, 0, , 0, 0, 0],
					[0, 0, 0, 0, , 0, 0, 0, 0],
					[0, 0, 0, 0, , 0, 0, 0, 0],
					[1, 1, 1, 1, , 1, 1, 1, 1],
					[0, 4, 4, 4, , 4, 4, 4, 0],
					[, 1, 1, 1, , 1, 1, 1],
					[, 0, 0, 0, , 0, 0, 0]
				]
			}, {
				time: 360,
				map: [
					[, , , 0, 0, 0, , , ],
					[, , 0, 0, 1, 0, 0, , ],
					[, , 0, 1, 1, 1, 0, , ],
					[, , 0, 5, 5, 5, 0, , ],
					[, 0, 0, 1, 1, 1, 0, 0],
					[, 0, 0, 2, 2, 2, 0, 0],
					[, 1, 1, 0, 0, 0, 1, 1],
					[, 0, 0, , 0, , 0, 0]
				]
			}, {
				time: 360,
				map: [
					[0, 0, 0, 0, , 0, 0, 0, 0],
					[, 0, 0, 0, , 0, 0, 0],
					[, , 0, 0, 0, 0, 0, , ],
					[, 0, 2, 2, 0, 2, 2, 0],
					[0, 0, , 0, 0, 0, , 0, 0],
					[1, 1, 1, 0, , 0, 1, 1, 1],
					[0, 1, 1, 1, 0, 1, 1, 1, 0],
					[, 0, 0, 0, , 0, 0, 0]
				]
			}, {
				time: 360,
				map: [
					[1, 1, 1, 0, 0, 0, 1, 1, 1],
					[1, 2, 1, 0, , 0, 1, 2, 1],
					[1, 1, 1, , 0, , 1, 1, 1],
					[0, 0, , 0, 0, 0, , 0, 0],
					[0, , 0, 0, 0, 0, 0, , 0],
					[, , 0, 5, 5, 5, 0, , ],
					[, 0, 0, 1, 2, 1, 0, 0],
					[, 0, 0, 1, 1, 1, 0, 0]
				]
			}, {
				time: 300,
				map: [
					[, , , 0, 0, 0, , , ],
					[, , 0, 1, 1, 1, 0, , ],
					[, 0, 0, 1, 1, 1, 0, 0],
					[0, 0, 0, 0, 0, 0, 0, 0, 0],
					[0, 0, , , 0, , , 0, 0],
					[0, 0, 0, 2, 8, 2, 0, 0, 0],
					[, 0, 1, 0, 2, 0, 1, 0, 0],
					[, 0, 1, 0, 0, 0, 1, 0]
				]
			}, {
				time: 360,
				map: [
					[, 0, 0, 0, , 0, 0, 0],
					[, 0, 0, 1, 0, 1, 0, 0],
					[0, 0, 1, 1, 0, 1, 1, 0, 0],
					[1, 1, 1, , 0, , 1, 1, 1],
					[1, 8, 1, 1, 1, 1, 1, 8, 1],
					[, 0, , 1, 2, 1, , 0],
					[, 0, 0, 1, 1, 1, 0, 0],
					[0, 0, 0, 1, 1, 1, 0, 0, 0]
				]
			}, {
				time: 360,
				map: [
					[0, 0, 0, 0, 0, 0, 0, 0, 0],
					[, 0, 1, 1, 1, 1, 1, 0],
					[, 0, 1, 1, 1, 1, 1, 0],
					[, , 0, , 0, , 0, , ],
					[, 0, , 0, , 0, , 0],
					[, 0, 1, 1, 1, 1, 1, 0],
					[0, 0, 1, 2, 2, 2, 1, 0, 0],
					[0, 0, 1, 2, 2, 2, 1, 0, 0]
				]
			}, {
				time: 360,
				map: [
					[, , 0, 0, , 0, 0, , ],
					[, 0, 0, 0, , 0, 0, 0],
					[0, 0, 2, 0, , 0, 2, 0, 0],
					[0, 0, 2, 0, , 0, 2, 0, 0],
					[, 5, 1, 5, 1, 5, 1, 5],
					[, 0, 0, 2, 2, 2, 0, 0],
					[, , 0, 0, 0, 0, 0, , ],
					[, , 0, 0, , 0, 0, , ]
				]
			}, {
				time: 360,
				map: [
					[0, 0, , 0, 0, 0, , 0, 0],
					[0, 0, , 0, 0, 0, , 0, 0],
					[0, 0, 0, 0, , 0, 0, 0, 0],
					[, 0, 1, 0, , 0, 1, 0],
					[0, , 1, 1, , 1, 1, , 0],
					[0, 0, 1, 2, 0, 2, 1, 0, 0],
					[0, 1, 0, 2, 0, 2, 0, 1, 0],
					[0, 0, , 0, 0, 0, , 0, 0]
				]
			}, {
				time: 360,
				map: [
					[, , 0, 0, 0, 0, 0, , ],
					[, 0, 1, 1, 1, 1, 1, 1],
					[0, 5, 5, 5, 1, 5, 5, 5, 0],
					[0, 1, 1, 2, 2, 2, 1, 1, 0],
					[0, 1, 1, 10, 10, 10, 1, 1, 0],
					[0, 1, 1, 1, 1, 1, 1, 1, 0],
					[, 0, 1, 1, 1, 1, 1, 0],
					[, , 0, 0, 0, 0, 0, , ]
				]
			}];
package com.mx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 小米线儿
 * @time 2019/2/21 0021
 * @QQ 723109056
 * @blog https://blog.csdn.net/qq_31407255
 */

@Controller
@RequestMapping("/games")
public class MiniGamesController {

    @RequestMapping("/airWar")
    public String airWarIndex(){
        return "games/airwar/index";
    }



    @RequestMapping("/biaobai")
    public String biaobaoIndex(){
        return "games/biaobai/index";
    }

    @RequestMapping("/speedsound")
    public String speedsound(){
        return "games/speedsound/index";
    }

    @RequestMapping("/guessidioms")
    public String guessidioms(){
        return "games/guessidioms/index";
    }

    @RequestMapping("/gobang")
    public String gobang(){
        return "games/gobang/index";
    }

    @RequestMapping("/g2048")
    public String g2048(){
        return "games/g2048/index";
    }

    @RequestMapping("/kxgame")
    public String kxgame(){
        return "games/kxgame/index";
    }

    @RequestMapping("/courtship")
    public String courtship(){
        return "games/courtship/index";
    }

    @RequestMapping("/love520")
    public String love520(){
        return "games/love520/index";
    }

    @RequestMapping("/loveyou")
    public String loveyou(){
        return "games/loveyou/index";
    }

    @RequestMapping("/loveu")
    public String loveu(){
        return "games/loveu/index";
    }
}

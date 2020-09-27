package com.leidi.trainalarm.bean;

import java.util.List;

/**
 * @author 阎
 * @date 2020/9/8
 * @description
 */
public class RailWayBean {


    /**
     * msg : 操作成功
     * code : 200
     * data : [{"railwaylineId":46,"railwaylineNo":"L006","railwaylineName":"阿房沣惠测试线","railwaylineMileage":"19.37","jjgjRailwaylineEntity":[{"longitude":"108.83584","latitude":"34.270092","mileage":"0.0","pointwkt":"POINT M (108.83584 34.270092 0)","longitudeBD":"108.84686791183415","latitudeBD":"34.27487087996841"},{"longitude":"108.83606","latitude":"34.268703","mileage":"150.0","pointwkt":"POINT M (108.83606 34.268703 150)","longitudeBD":"108.84708874387213","latitudeBD":"34.27348119129873"},{"longitude":"108.83942","latitude":"34.26405","mileage":"760.0","pointwkt":"POINT M (108.83942 34.26405 760)","longitudeBD":"108.85044954099676","latitudeBD":"34.268814435677946"},{"longitude":"108.84024","latitude":"34.262806","mileage":"910.0","pointwkt":"POINT M (108.84024 34.262806 910)","longitudeBD":"108.85127036052803","latitudeBD":"34.26756559150477"},{"longitude":"108.841125","latitude":"34.25804","mileage":"1450.0","pointwkt":"POINT M (108.841125 34.25804 1450)","longitudeBD":"108.85215959935579","latitudeBD":"34.26279343581402"},{"longitude":"108.84161","latitude":"34.257183","mileage":"1550.0","pointwkt":"POINT M (108.84161 34.257183 1550)","longitudeBD":"108.85264539202474","latitudeBD":"34.26193292783702"},{"longitude":"108.84295","latitude":"34.256325","mileage":"1710.0","pointwkt":"POINT M (108.84295 34.256325 1710)","longitudeBD":"108.85398655488754","latitudeBD":"34.26106434966003"},{"longitude":"108.84488","latitude":"34.25125","mileage":"2300.0","pointwkt":"POINT M (108.84488 34.25125 2300)","longitudeBD":"108.85592223732068","latitudeBD":"34.25597115704635"},{"longitude":"108.8456","latitude":"34.24831","mileage":"2630.0","pointwkt":"POINT M (108.8456 34.24831 2630)","longitudeBD":"108.85664518973161","latitudeBD":"34.25302343814276"},{"longitude":"108.84655","latitude":"34.24668","mileage":"2830.0","pointwkt":"POINT M (108.84655 34.24668 2830)","longitudeBD":"108.85759751340703","latitudeBD":"34.25138291447645"},{"longitude":"108.847595","latitude":"34.24267","mileage":"3290.0","pointwkt":"POINT M (108.847595 34.24267 3290)","longitudeBD":"108.85864636526932","latitudeBD":"34.24736011988884"},{"longitude":"108.84914","latitude":"34.23829","mileage":"3800.0","pointwkt":"POINT M (108.84914 34.23829 3800)","longitudeBD":"108.86019588317166","latitudeBD":"34.242959857938274"},{"longitude":"108.85301","latitude":"34.226917","mileage":"5110.0","pointwkt":"POINT M (108.85301 34.226917 5110)","longitudeBD":"108.86407519002977","latitudeBD":"34.231528097044816"},{"longitude":"108.85019","latitude":"34.225224","mileage":"5430.0","pointwkt":"POINT M (108.85019 34.225224 5430)","longitudeBD":"108.86124725910724","latitudeBD":"34.229875125684686"},{"longitude":"108.842575","latitude":"34.220695","mileage":"6290.0","pointwkt":"POINT M (108.842575 34.220695 6290)","longitudeBD":"108.8536195150835","latitudeBD":"34.22542751750486"},{"longitude":"108.83386","latitude":"34.2168","mileage":"7200.0","pointwkt":"POINT M (108.83386 34.2168 7200)","longitudeBD":"108.84490930236798","latitudeBD":"34.22156828597972"},{"longitude":"108.83265","latitude":"34.21619","mileage":"7330.0","pointwkt":"POINT M (108.83265 34.21619 7330)","longitudeBD":"108.84370158799294","latitudeBD":"34.220957922463896"},{"longitude":"108.83128","latitude":"34.213528","mileage":"7650.0","pointwkt":"POINT M (108.83128 34.213528 7650)","longitudeBD":"108.84233297277162","latitudeBD":"34.21829277764234"},{"longitude":"108.83077","latitude":"34.212414","mileage":"7790.0","pointwkt":"POINT M (108.83077 34.212414 7790)","longitudeBD":"108.84182345140347","latitudeBD":"34.21717709706067"},{"longitude":"108.8296","latitude":"34.210094","mileage":"8060.0","pointwkt":"POINT M (108.8296 34.210094 8060)","longitudeBD":"108.84065492400231","latitudeBD":"34.21485250783579"},{"longitude":"108.828896","latitude":"34.20888","mileage":"8220.0","pointwkt":"POINT M (108.828896 34.20888 8220)","longitudeBD":"108.83995211122163","latitudeBD":"34.213635283568486"},{"longitude":"108.82548","latitude":"34.205944","mileage":"8670.0","pointwkt":"POINT M (108.82548 34.205944 8670)","longitudeBD":"108.83654652403126","latitudeBD":"34.21067967298366"},{"longitude":"108.81982","latitude":"34.20074","mileage":"9450.0","pointwkt":"POINT M (108.81982 34.20074 9450)","longitudeBD":"108.83090924503584","latitudeBD":"34.205423477879464"},{"longitude":"108.8194","latitude":"34.200054","mileage":"9530.0","pointwkt":"POINT M (108.8194 34.200054 9530)","longitudeBD":"108.83049086740313","latitudeBD":"34.20473254994715"},{"longitude":"108.818375","latitude":"34.197166","mileage":"9870.0","pointwkt":"POINT M (108.818375 34.197166 9870)","longitudeBD":"108.82946868368327","latitudeBD":"34.201831302330696"},{"longitude":"108.817764","latitude":"34.19649","mileage":"9960.0","pointwkt":"POINT M (108.817764 34.19649 9960)","longitudeBD":"108.8288605730934","latitudeBD":"34.20114779990472"},{"longitude":"108.813774","latitude":"34.194443","mileage":"10390.0","pointwkt":"POINT M (108.813774 34.194443 10390)","longitudeBD":"108.82489330640631","latitudeBD":"34.19904869613568"},{"longitude":"108.81289","latitude":"34.19383","mileage":"10500.0","pointwkt":"POINT M (108.81289 34.19383 10500)","longitudeBD":"108.82401445578147","latitudeBD":"34.19842312110006"},{"longitude":"108.80711","latitude":"34.188683","mileage":"11280.0","pointwkt":"POINT M (108.80711 34.188683 11280)","longitudeBD":"108.81826869964449","latitudeBD":"34.19318783092051"},{"longitude":"108.80575","latitude":"34.187973","mileage":"11430.0","pointwkt":"POINT M (108.80575 34.187973 11430)","longitudeBD":"108.81691743453023","latitudeBD":"34.19245657305539"},{"longitude":"108.79943","latitude":"34.186985","mileage":"12020.0","pointwkt":"POINT M (108.79943 34.186985 12020)","longitudeBD":"108.81063909143064","latitudeBD":"34.19137205456517"},{"longitude":"108.79653","latitude":"34.186512","mileage":"12290.0","pointwkt":"POINT M (108.79653 34.186512 12290)","longitudeBD":"108.8077569746342","latitudeBD":"34.19085740475194"},{"longitude":"108.78894","latitude":"34.184475","mileage":"13030.0","pointwkt":"POINT M (108.78894 34.184475 13030)","longitudeBD":"108.80020643275671","latitudeBD":"34.188728222967875"},{"longitude":"108.7736","latitude":"34.17527","mileage":"14770.0","pointwkt":"POINT M (108.7736 34.17527 14770)","longitudeBD":"108.78489881165282","latitudeBD":"34.17945729685687"},{"longitude":"108.77115","latitude":"34.171577","mileage":"15240.0","pointwkt":"POINT M (108.77115 34.171577 15240)","longitudeBD":"108.78244728444812","latitudeBD":"34.17577134070302"},{"longitude":"108.77025","latitude":"34.16834","mileage":"15610.0","pointwkt":"POINT M (108.77025 34.16834 15610)","longitudeBD":"108.78154648284979","latitudeBD":"34.17253774212328"},{"longitude":"108.762245","latitude":"34.14201","mileage":"18620.0","pointwkt":"POINT M (108.762245 34.14201 18620)","longitudeBD":"108.77353987860134","latitudeBD":"34.14627219443664"},{"longitude":"108.76035","latitude":"34.135464","mileage":"19370.0","pointwkt":"POINT M (108.76035 34.135464 19370)","longitudeBD":"108.77164399153283","latitudeBD":"34.13974875614643"}]},{"railwaylineId":50,"railwaylineNo":"L007","railwaylineName":"安远测试线","railwaylineMileage":"25.55","jjgjRailwaylineEntity":[{"longitude":"108.77083","latitude":"34.23721","mileage":"0.0","pointwkt":"POINT M (108.77083 34.23721 0)","longitudeBD":"108.78216988211763","latitudeBD":"34.24143996923725"},{"longitude":"108.77701","latitude":"34.229332","mileage":"1040.0","pointwkt":"POINT M (108.77701 34.229332 1040)","longitudeBD":"108.78834902504003","latitudeBD":"34.23354650472326"},{"longitude":"108.78433","latitude":"34.2233","mileage":"1990.0","pointwkt":"POINT M (108.78433 34.2233 1990)","longitudeBD":"108.79565027689843","latitudeBD":"34.22753848355145"},{"longitude":"108.78636","latitude":"34.22252","mileage":"2190.0","pointwkt":"POINT M (108.78636 34.22252 2190)","longitudeBD":"108.79767246280399","latitudeBD":"34.22677302961917"},{"longitude":"108.78808","latitude":"34.22085","mileage":"2440.0","pointwkt":"POINT M (108.78808 34.22085 2440)","longitudeBD":"108.79938435872236","latitudeBD":"34.22511714898413"},{"longitude":"108.79462","latitude":"34.218227","mileage":"3110.0","pointwkt":"POINT M (108.79462 34.218227 3110)","longitudeBD":"108.80588909258464","latitudeBD":"34.22256646066518"},{"longitude":"108.79662","latitude":"34.217915","mileage":"3290.0","pointwkt":"POINT M (108.79662 34.217915 3290)","longitudeBD":"108.8078769493935","latitudeBD":"34.22228114257919"},{"longitude":"108.800865","latitude":"34.21645","mileage":"3720.0","pointwkt":"POINT M (108.800865 34.21645 3720)","longitudeBD":"108.81209370275702","latitudeBD":"34.22087677896021"},{"longitude":"108.80259","latitude":"34.216026","mileage":"3880.0","pointwkt":"POINT M (108.80259 34.216026 3880)","longitudeBD":"108.81380682488638","latitudeBD":"34.22047865789957"},{"longitude":"108.80357","latitude":"34.21481","mileage":"4050.0","pointwkt":"POINT M (108.80357 34.21481 4050)","longitudeBD":"108.81477917734814","latitudeBD":"34.21927694236724"},{"longitude":"108.803635","latitude":"34.212795","mileage":"4270.0","pointwkt":"POINT M (108.803635 34.212795 4270)","longitudeBD":"108.81484196197883","latitudeBD":"34.21726172428001"},{"longitude":"108.80756","latitude":"34.20692","mileage":"5010.0","pointwkt":"POINT M (108.80756 34.20692 5010)","longitudeBD":"108.8187342845261","latitudeBD":"34.211443252083896"},{"longitude":"108.8178","latitude":"34.207737","mileage":"5960.0","pointwkt":"POINT M (108.8178 34.207737 5960)","longitudeBD":"108.82890831800815","latitudeBD":"34.21240253503116"},{"longitude":"108.81853","latitude":"34.208645","mileage":"6080.0","pointwkt":"POINT M (108.81853 34.208645 6080)","longitudeBD":"108.82963495443396","latitudeBD":"34.21331952438127"},{"longitude":"108.81861","latitude":"34.21001","mileage":"6230.0","pointwkt":"POINT M (108.81861 34.21001 6230)","longitudeBD":"108.82971584921187","latitudeBD":"34.214686295109"},{"longitude":"108.82344","latitude":"34.211998","mileage":"6730.0","pointwkt":"POINT M (108.82344 34.211998 6730)","longitudeBD":"108.83452196139586","latitudeBD":"34.21672256894341"},{"longitude":"108.827","latitude":"34.213398","mileage":"7090.0","pointwkt":"POINT M (108.827 34.213398 7090)","longitudeBD":"108.83806750215003","latitudeBD":"34.218147434185084"},{"longitude":"108.82757","latitude":"34.213326","mileage":"7150.0","pointwkt":"POINT M (108.82757 34.213326 7150)","longitudeBD":"108.83863520818946","latitudeBD":"34.21807830693746"},{"longitude":"108.82832","latitude":"34.21311","mileage":"7220.0","pointwkt":"POINT M (108.82832 34.21311 7220)","longitudeBD":"108.83938220852747","latitudeBD":"34.21786560698587"},{"longitude":"108.83204","latitude":"34.213142","mileage":"7560.0","pointwkt":"POINT M (108.83204 34.213142 7560)","longitudeBD":"108.84309054895245","latitudeBD":"34.21790761552709"},{"longitude":"108.83569","latitude":"34.21315","mileage":"7900.0","pointwkt":"POINT M (108.83569 34.21315 7900)","longitudeBD":"108.84673285212794","latitudeBD":"34.217913684406916"},{"longitude":"108.86498","latitude":"34.21347","mileage":"10600.0","pointwkt":"POINT M (108.86498 34.21347 10600)","longitudeBD":"108.876081634195","latitudeBD":"34.21786605219678"},{"longitude":"108.86503","latitude":"34.21812","mileage":"11110.0","pointwkt":"POINT M (108.86503 34.21812 11110)","longitudeBD":"108.87613563975395","latitudeBD":"34.22251783719123"},{"longitude":"108.86408","latitude":"34.221455","mileage":"11490.0","pointwkt":"POINT M (108.86408 34.221455 11490)","longitudeBD":"108.87518375604968","latitudeBD":"34.22587214028824"},{"longitude":"108.865845","latitude":"34.221596","mileage":"11660.0","pointwkt":"POINT M (108.865845 34.221596 11660)","longitudeBD":"108.87695645502802","latitudeBD":"34.225980683336026"},{"longitude":"108.86762","latitude":"34.221294","mileage":"11820.0","pointwkt":"POINT M (108.86762 34.221294 11820)","longitudeBD":"108.87873902613919","latitudeBD":"34.225645963789866"},{"longitude":"108.868805","latitude":"34.221596","mileage":"11940.0","pointwkt":"POINT M (108.868805 34.221596 11940)","longitudeBD":"108.87992938820229","latitudeBD":"34.22592659438601"},{"longitude":"108.883415","latitude":"34.22181","mileage":"13400.0","pointwkt":"POINT M (108.883415 34.22181 13400)","longitudeBD":"108.89459532068003","latitudeBD":"34.225919493137184"},{"longitude":"108.88344","latitude":"34.22733","mileage":"13920.0","pointwkt":"POINT M (108.88344 34.22733 13920)","longitudeBD":"108.89462283477768","latitudeBD":"34.231441836922386"},{"longitude":"108.885216","latitude":"34.22738","mileage":"14090.0","pointwkt":"POINT M (108.885216 34.22738 14090)","longitudeBD":"108.89640361886691","latitudeBD":"34.23147392871728"},{"longitude":"108.88518","latitude":"34.238895","mileage":"15360.0","pointwkt":"POINT M (108.88518 34.238895 15360)","longitudeBD":"108.89636758747244","latitudeBD":"34.242993235192344"},{"longitude":"108.8852","latitude":"34.25381","mileage":"17020.0","pointwkt":"POINT M (108.8852 34.25381 17020)","longitudeBD":"108.89637874754587","latitudeBD":"34.2579105660291"},{"longitude":"108.88983","latitude":"34.253883","mileage":"17440.0","pointwkt":"POINT M (108.88983 34.253883 17440)","longitudeBD":"108.90101816631002","latitudeBD":"34.25794906597648"},{"longitude":"108.89537","latitude":"34.252357","mileage":"17980.0","pointwkt":"POINT M (108.89537 34.252357 17980)","longitudeBD":"108.90656456153052","latitudeBD":"34.25640687293229"},{"longitude":"108.905624","latitude":"34.252533","mileage":"18930.0","pointwkt":"POINT M (108.905624 34.252533 18930)","longitudeBD":"108.9168097210078","latitudeBD":"34.25662796978283"},{"longitude":"108.9188","latitude":"34.252533","mileage":"20140.0","pointwkt":"POINT M (108.9188 34.252533 20140)","longitudeBD":"108.92994768875339","latitudeBD":"34.25680467672199"},{"longitude":"108.9191","latitude":"34.260765","mileage":"21050.0","pointwkt":"POINT M (108.9191 34.260765 21050)","longitudeBD":"108.93023940759652","latitudeBD":"34.26504253167119"},{"longitude":"108.917816","latitude":"34.27112","mileage":"22210.0","pointwkt":"POINT M (108.917816 34.27112 22210)","longitudeBD":"108.92895079701987","latitudeBD":"34.27537706475528"},{"longitude":"108.917984","latitude":"34.27619","mileage":"22770.0","pointwkt":"POINT M (108.917984 34.27619 22770)","longitudeBD":"108.92911454299526","latitudeBD":"34.280450658270816"},{"longitude":"108.91919","latitude":"34.278175","mileage":"23010.0","pointwkt":"POINT M (108.91919 34.278175 23010)","longitudeBD":"108.93031510019323","latitudeBD":"34.28245633561275"},{"longitude":"108.942406","latitude":"34.278214","mileage":"25150.0","pointwkt":"POINT M (108.942406 34.278214 25150)","longitudeBD":"108.9534688389075","latitudeBD":"34.28288434035065"},{"longitude":"108.94238","latitude":"34.28181","mileage":"25550.0","pointwkt":"POINT M (108.94238 34.28181 25550)","longitudeBD":"108.9534410946006","latitudeBD":"34.28648088145887"}]}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * railwaylineId : 46
         * railwaylineNo : L006
         * railwaylineName : 阿房沣惠测试线
         * railwaylineMileage : 19.37
         * jjgjRailwaylineEntity : [{"longitude":"108.83584","latitude":"34.270092","mileage":"0.0","pointwkt":"POINT M (108.83584 34.270092 0)","longitudeBD":"108.84686791183415","latitudeBD":"34.27487087996841"},{"longitude":"108.83606","latitude":"34.268703","mileage":"150.0","pointwkt":"POINT M (108.83606 34.268703 150)","longitudeBD":"108.84708874387213","latitudeBD":"34.27348119129873"},{"longitude":"108.83942","latitude":"34.26405","mileage":"760.0","pointwkt":"POINT M (108.83942 34.26405 760)","longitudeBD":"108.85044954099676","latitudeBD":"34.268814435677946"},{"longitude":"108.84024","latitude":"34.262806","mileage":"910.0","pointwkt":"POINT M (108.84024 34.262806 910)","longitudeBD":"108.85127036052803","latitudeBD":"34.26756559150477"},{"longitude":"108.841125","latitude":"34.25804","mileage":"1450.0","pointwkt":"POINT M (108.841125 34.25804 1450)","longitudeBD":"108.85215959935579","latitudeBD":"34.26279343581402"},{"longitude":"108.84161","latitude":"34.257183","mileage":"1550.0","pointwkt":"POINT M (108.84161 34.257183 1550)","longitudeBD":"108.85264539202474","latitudeBD":"34.26193292783702"},{"longitude":"108.84295","latitude":"34.256325","mileage":"1710.0","pointwkt":"POINT M (108.84295 34.256325 1710)","longitudeBD":"108.85398655488754","latitudeBD":"34.26106434966003"},{"longitude":"108.84488","latitude":"34.25125","mileage":"2300.0","pointwkt":"POINT M (108.84488 34.25125 2300)","longitudeBD":"108.85592223732068","latitudeBD":"34.25597115704635"},{"longitude":"108.8456","latitude":"34.24831","mileage":"2630.0","pointwkt":"POINT M (108.8456 34.24831 2630)","longitudeBD":"108.85664518973161","latitudeBD":"34.25302343814276"},{"longitude":"108.84655","latitude":"34.24668","mileage":"2830.0","pointwkt":"POINT M (108.84655 34.24668 2830)","longitudeBD":"108.85759751340703","latitudeBD":"34.25138291447645"},{"longitude":"108.847595","latitude":"34.24267","mileage":"3290.0","pointwkt":"POINT M (108.847595 34.24267 3290)","longitudeBD":"108.85864636526932","latitudeBD":"34.24736011988884"},{"longitude":"108.84914","latitude":"34.23829","mileage":"3800.0","pointwkt":"POINT M (108.84914 34.23829 3800)","longitudeBD":"108.86019588317166","latitudeBD":"34.242959857938274"},{"longitude":"108.85301","latitude":"34.226917","mileage":"5110.0","pointwkt":"POINT M (108.85301 34.226917 5110)","longitudeBD":"108.86407519002977","latitudeBD":"34.231528097044816"},{"longitude":"108.85019","latitude":"34.225224","mileage":"5430.0","pointwkt":"POINT M (108.85019 34.225224 5430)","longitudeBD":"108.86124725910724","latitudeBD":"34.229875125684686"},{"longitude":"108.842575","latitude":"34.220695","mileage":"6290.0","pointwkt":"POINT M (108.842575 34.220695 6290)","longitudeBD":"108.8536195150835","latitudeBD":"34.22542751750486"},{"longitude":"108.83386","latitude":"34.2168","mileage":"7200.0","pointwkt":"POINT M (108.83386 34.2168 7200)","longitudeBD":"108.84490930236798","latitudeBD":"34.22156828597972"},{"longitude":"108.83265","latitude":"34.21619","mileage":"7330.0","pointwkt":"POINT M (108.83265 34.21619 7330)","longitudeBD":"108.84370158799294","latitudeBD":"34.220957922463896"},{"longitude":"108.83128","latitude":"34.213528","mileage":"7650.0","pointwkt":"POINT M (108.83128 34.213528 7650)","longitudeBD":"108.84233297277162","latitudeBD":"34.21829277764234"},{"longitude":"108.83077","latitude":"34.212414","mileage":"7790.0","pointwkt":"POINT M (108.83077 34.212414 7790)","longitudeBD":"108.84182345140347","latitudeBD":"34.21717709706067"},{"longitude":"108.8296","latitude":"34.210094","mileage":"8060.0","pointwkt":"POINT M (108.8296 34.210094 8060)","longitudeBD":"108.84065492400231","latitudeBD":"34.21485250783579"},{"longitude":"108.828896","latitude":"34.20888","mileage":"8220.0","pointwkt":"POINT M (108.828896 34.20888 8220)","longitudeBD":"108.83995211122163","latitudeBD":"34.213635283568486"},{"longitude":"108.82548","latitude":"34.205944","mileage":"8670.0","pointwkt":"POINT M (108.82548 34.205944 8670)","longitudeBD":"108.83654652403126","latitudeBD":"34.21067967298366"},{"longitude":"108.81982","latitude":"34.20074","mileage":"9450.0","pointwkt":"POINT M (108.81982 34.20074 9450)","longitudeBD":"108.83090924503584","latitudeBD":"34.205423477879464"},{"longitude":"108.8194","latitude":"34.200054","mileage":"9530.0","pointwkt":"POINT M (108.8194 34.200054 9530)","longitudeBD":"108.83049086740313","latitudeBD":"34.20473254994715"},{"longitude":"108.818375","latitude":"34.197166","mileage":"9870.0","pointwkt":"POINT M (108.818375 34.197166 9870)","longitudeBD":"108.82946868368327","latitudeBD":"34.201831302330696"},{"longitude":"108.817764","latitude":"34.19649","mileage":"9960.0","pointwkt":"POINT M (108.817764 34.19649 9960)","longitudeBD":"108.8288605730934","latitudeBD":"34.20114779990472"},{"longitude":"108.813774","latitude":"34.194443","mileage":"10390.0","pointwkt":"POINT M (108.813774 34.194443 10390)","longitudeBD":"108.82489330640631","latitudeBD":"34.19904869613568"},{"longitude":"108.81289","latitude":"34.19383","mileage":"10500.0","pointwkt":"POINT M (108.81289 34.19383 10500)","longitudeBD":"108.82401445578147","latitudeBD":"34.19842312110006"},{"longitude":"108.80711","latitude":"34.188683","mileage":"11280.0","pointwkt":"POINT M (108.80711 34.188683 11280)","longitudeBD":"108.81826869964449","latitudeBD":"34.19318783092051"},{"longitude":"108.80575","latitude":"34.187973","mileage":"11430.0","pointwkt":"POINT M (108.80575 34.187973 11430)","longitudeBD":"108.81691743453023","latitudeBD":"34.19245657305539"},{"longitude":"108.79943","latitude":"34.186985","mileage":"12020.0","pointwkt":"POINT M (108.79943 34.186985 12020)","longitudeBD":"108.81063909143064","latitudeBD":"34.19137205456517"},{"longitude":"108.79653","latitude":"34.186512","mileage":"12290.0","pointwkt":"POINT M (108.79653 34.186512 12290)","longitudeBD":"108.8077569746342","latitudeBD":"34.19085740475194"},{"longitude":"108.78894","latitude":"34.184475","mileage":"13030.0","pointwkt":"POINT M (108.78894 34.184475 13030)","longitudeBD":"108.80020643275671","latitudeBD":"34.188728222967875"},{"longitude":"108.7736","latitude":"34.17527","mileage":"14770.0","pointwkt":"POINT M (108.7736 34.17527 14770)","longitudeBD":"108.78489881165282","latitudeBD":"34.17945729685687"},{"longitude":"108.77115","latitude":"34.171577","mileage":"15240.0","pointwkt":"POINT M (108.77115 34.171577 15240)","longitudeBD":"108.78244728444812","latitudeBD":"34.17577134070302"},{"longitude":"108.77025","latitude":"34.16834","mileage":"15610.0","pointwkt":"POINT M (108.77025 34.16834 15610)","longitudeBD":"108.78154648284979","latitudeBD":"34.17253774212328"},{"longitude":"108.762245","latitude":"34.14201","mileage":"18620.0","pointwkt":"POINT M (108.762245 34.14201 18620)","longitudeBD":"108.77353987860134","latitudeBD":"34.14627219443664"},{"longitude":"108.76035","latitude":"34.135464","mileage":"19370.0","pointwkt":"POINT M (108.76035 34.135464 19370)","longitudeBD":"108.77164399153283","latitudeBD":"34.13974875614643"}]
         */

        private int railwaylineId;
        private String railwaylineNo;
        private String railwaylineName;
        private String railwaylineMileage;
        private List<JjgjRailwaylineEntityBean> jjgjRailwaylineEntity;

        public int getRailwaylineId() {
            return railwaylineId;
        }

        public void setRailwaylineId(int railwaylineId) {
            this.railwaylineId = railwaylineId;
        }

        public String getRailwaylineNo() {
            return railwaylineNo;
        }

        public void setRailwaylineNo(String railwaylineNo) {
            this.railwaylineNo = railwaylineNo;
        }

        public String getRailwaylineName() {
            return railwaylineName;
        }

        public void setRailwaylineName(String railwaylineName) {
            this.railwaylineName = railwaylineName;
        }

        public String getRailwaylineMileage() {
            return railwaylineMileage;
        }

        public void setRailwaylineMileage(String railwaylineMileage) {
            this.railwaylineMileage = railwaylineMileage;
        }

        public List<JjgjRailwaylineEntityBean> getJjgjRailwaylineEntity() {
            return jjgjRailwaylineEntity;
        }

        public void setJjgjRailwaylineEntity(List<JjgjRailwaylineEntityBean> jjgjRailwaylineEntity) {
            this.jjgjRailwaylineEntity = jjgjRailwaylineEntity;
        }

        public static class JjgjRailwaylineEntityBean {
            /**
             * longitude : 108.83584
             * latitude : 34.270092
             * mileage : 0.0
             * pointwkt : POINT M (108.83584 34.270092 0)
             * longitudeBD : 108.84686791183415
             * latitudeBD : 34.27487087996841
             */

            private String longitude;
            private String latitude;
            private String mileage;
            private String pointwkt;
            private String longitudeBD;
            private String latitudeBD;

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getMileage() {
                return mileage;
            }

            public void setMileage(String mileage) {
                this.mileage = mileage;
            }

            public String getPointwkt() {
                return pointwkt;
            }

            public void setPointwkt(String pointwkt) {
                this.pointwkt = pointwkt;
            }

            public String getLongitudeBD() {
                return longitudeBD;
            }

            public void setLongitudeBD(String longitudeBD) {
                this.longitudeBD = longitudeBD;
            }

            public String getLatitudeBD() {
                return latitudeBD;
            }

            public void setLatitudeBD(String latitudeBD) {
                this.latitudeBD = latitudeBD;
            }
        }
    }
}

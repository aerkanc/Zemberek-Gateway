public class ZemberekGateway {

    public TurkiyeTurkcesi turkiyeTurkcesi;
    public Zemberek zemberek;

    public TurkiyeTurkcesi getTurkiyeTurkcesi(){
        return turkiyeTurkcesi;
    }

    public Zemberek getZemberek(){
        return zemberek;
    }

    public String deasciify(String text){
        Deasciifier d = new Deasciifier();
        d.setAsciiString(text);
        return d.convertToTurkish();
    }

    public Kelime[] cozumle(String word){
        return zemberek.cozumleyici().cozumle(word,CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
    }

    public static void main(String[] args) {
        ZemberekGateway zemberekGateway = new ZemberekGateway();
        zemberekGateway.turkiyeTurkcesi = new TurkiyeTurkcesi();
        zemberekGateway.zemberek = new Zemberek(zemberekGateway.getTurkiyeTurkcesi());
        GatewayServer gatewayServer = new GatewayServer(zemberekGateway);
        gatewayServer.start();
        System.out.println("Gateway Server Started");
//        Kelime[] tres = zemberekGateway.zemberek.cozumleyici().cozumle("yiyelim", CozumlemeSeviyesi.TUM_KOK_VE_EKLER);
//        System.out.println(tres);
    }
}
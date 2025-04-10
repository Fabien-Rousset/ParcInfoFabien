package fr.afpa.pompey.cda17.ParcInfo.models;

public enum TypePeripherique {
    CAMERA_WEB(0),
    CARTE_CAPTURE(1),
    CASQUE(2),
    CASQUE_MICROPHONE(3),
    CASQUE_VR(4),
    CLAVIER(5),
    DISQUE_EXTERNE(6),
    ECRAN(7),
    ENCEINTES(8),
    HUB_USB(9),
    IMPRIMANTE(10),
    IMPRIMANTE_SCANNER(11),
    MICROPHONE(12),
    PHOTOCOPIEUR(13),
    SCANNER(14),
    SOURIS(15),
    TABLETTE_GRAPHIQUE(16);

    private final int value;

    TypePeripherique(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.name().charAt(0) + this.name()
                .substring(1)
                .replace('_',' ')
                .toLowerCase();
    }
}
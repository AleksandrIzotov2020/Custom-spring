package org.example;

/*  S- Single responsibility  (Одна ответственность, те класс отвечает за одну и только одну функцию (дезинфекция комнаты))
*   O- Open close principle
*   L-
*   I-
*   D- Dependency inversion
* */

public class CoronaDesinfector {

    /*Диктор*/
    private Announcer announcer = ObjectFactory.getInstance().getBean(Announcer.class);
    private Policeman policeman= ObjectFactory.getInstance().getBean(Policeman.class);

    public void start(Room room){
        announcer.announce("Начинаем дезинфекцию. Всем выйти из комнаты!");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Рискните зайти обратно");

    }

    /*Single responsibility)*/
    private void desinfect(Room room){
        System.out.println("Проводится дезинфекция!");
    }
}

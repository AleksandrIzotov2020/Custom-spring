package org.example;

/*  S- Single responsibility  (Одна ответственность, те класс отвечает за одну и только одну функцию (дезинфекция комнаты))
*   O- Open close principle
*   L-
*   I-
*   D- Dependency inversion
* */
@WorkTime
public class CoronaDesinfector {

    @InjectByType
    private Announcer announcer; /*Диктор*/
    @InjectByType
    private Policeman policeman;
    @WorkTime
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

package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class App {
    private Scanner scanner = new Scanner(System.in);
    private List<WiseSaying> wiseSayings = new ArrayList<>();
    private int lastId = 0;

    public void run() {
        System.out.println("== 명언 앱 ==");

        while(true){
            System.out.print("명령) ");
            String cmd = scanner.nextLine();

            if(cmd.equals("종료")){
                break;
            }
            else if (cmd.equals("등록")) {
                actionWrite();
            }
            else if(cmd.equals("목록")) {
                actionList();
            } else if (cmd.startsWith("삭제")) {
                actionDelete(cmd);
            } else if (cmd.startsWith("수정")) {
                actionModify(cmd);
            }
        }

    }

    private void actionModify(String cmd) {
        String idStr = cmd.split("=")[1];
        int id = Integer.parseInt(idStr);
        WiseSaying wiseSaying = findById(id);

        if (wiseSaying == null) {
            System.out.println("%d번 명언은 존재 하지 않습니다.".formatted(id));
            return;
        }
        System.out.println("명언(기존) : %s\n".formatted(wiseSaying.getContent()));
        System.out.println("명언 : ");
        String content = scanner.nextLine();
        System.out.println("작가(기존) : %s\n".formatted(wiseSaying.getAuthor()));
        String author = scanner.nextLine();

        wiseSaying.setContent(content);
        wiseSaying.setAuthor(author);
    }
    /// ///////////////////////////////////////////////////////////////////
    private WiseSaying findById(int id){
        int foundedIndex = findIndexById(id);

        if (foundedIndex == -1) {
            return null;
        }

        return wiseSayings.get(foundedIndex);
    }

    private int findIndexById(int id) {
        return IntStream.range(0, wiseSayings.size())
                .filter(i -> wiseSayings.get(i).getId() == id)
                .findFirst()
                .orElse(-1);
    }

    private void actionDelete(String cmd) {
        String idStr = cmd.split("=")[1];
        int id = Integer.parseInt(idStr);
        boolean rst = delete(id);

        //rst가 존재 하지 않을 경우
        if (!rst) {
            System.out.println("%d번 명언은 존재하지 않습니다.".formatted(id));
            return;
        }

        for(int i = 0; i < wiseSayings.size(); i++){
            if(wiseSayings.get(i).id == id){
                wiseSayings.remove(i);
                System.out.println(id+"번 명언이 삭제되었습니다.");
                break;
            }
        }
    }

    private boolean delete(int deleteTarget) {

        int foundIndex = findIndexById(deleteTarget);

        if (foundIndex == -1) return false;

        wiseSayings.remove(foundIndex);

        return true;
    }

    private void actionList() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");


        wiseSayings.reversed()
                .stream()
                .forEach(w -> {
                    System.out.println(w.getId() + " / " + w.getAuthor() + " / " + w.getContent());
                });


    }

    private void actionWrite() {
        System.out.print("명언 : ");
        String content = scanner.nextLine();
        System.out.print("작가 : ");
        String author = scanner.nextLine();

        write(content ,author);
        System.out.println(lastId + "번 명언이 등록되었습니다.");
    }

    private void write(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(++lastId, content, author);
        wiseSayings.add(wiseSaying);
    }

}
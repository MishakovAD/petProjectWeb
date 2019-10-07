package YandexTest;

/*
Дан лист List<Integer> list = new ArrayList<>();

[1,2,0,0,4,1235125,0,235,0,0,0,25]

Сделать последовательность без нулей за линейной время.

Плохой пример:
int zeroIndex = 0;
boolean findZero = false;
for (int i = 0; i < list.size(); i++) {
    if (m(list.get(i) && !findZero) {
        zeroIndex = i;
        findZero = true;
    }

    if (!m(list.get(i)) && findZero) {
        list.set(zeroIndex, list.get(i));
        zeroIndex++;
    }
}

for (int i = zeroIndex; i < list.size(); i++) {
    list.set(i, 0);
}


private boolean m (Integer num) {
    int zero = 0;
    int numI = Integer.toPrimitive(num);
    return numI == zero;
}
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Exersise1 {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(0);
        list.add(0);
        list.add(13);
        list.add(14);
        list.add(0);
        list.add(0);
        list.add(0);
        list.add(25);
        new Exersise1().solution(list);
        System.out.println();
    }

    private void solution(List<Integer> list) {
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            if ((Integer) iterator.next() == 0) {
                iterator.remove();
            }
        }
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == 0) {
                list.remove(i);
                i--;
            }
        }
    }
}

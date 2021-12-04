package sweeper;

/* в этом классе описывается "верхний слой" нашего сапера, где располагаются
 *флажки, отметки взорванных бомб и тому подобное
 */
class Flag {

    // добавляем функционал для подсчета флажков
    public int countOfFlags; // ??объявляем переменную количества флагов

    private Matrix flagMap; // объявляем матрицу наших флагов
    private int countOfClosedBoxes; // объявляем переменную для количества закрытых ячеек
    // добавляем функционал для подсчета флажков

    void start() { // метод для запуска класса
        flagMap = new Matrix(Box.CLOSED); // инициализируем нашу матрицу с закрытыми ячейками
        countOfClosedBoxes = Ranges.getSize().x * Ranges.getSize().y; // получаем размер матрицы
        countOfFlags = 00; // ??инициализируем количество флагов (0)
    }

    Box get(Coord coord) { // геттер
        return flagMap.get(coord); // возвращаем координату ячейки нашей матрицы
    }

    public void setOpenedToBox(Coord coord) { // метод для открытия ячейки
        flagMap.set(coord, Box.OPENED); // по переданным координатам устанавливаем ячейку открытой
        countOfClosedBoxes--; // уменьшаем количество закрытых ячеек на единицу
    }

    public void toggleFlagedToBox(Coord coord) { // снимаем/устанавливаем флажок
        switch (flagMap.get(coord)) { // делаем свич в зависимости от того, что в ячейке
            // case FLAGED: setClosedToBox (coord); break; //если флажок - метод закрытия
            // ячейки
            case FLAGED:
                setQuestionToBox(coord);
                break; // ????если флажок - метод cтавим вопрос
            case INFORM:
                setClosedToBox(coord);
                break; // ????если вопрос - закрываем
            case CLOSED:
                setFlagedToBox(coord);
                break; // если открыто - метод установки флага
        }
    }

    private void setClosedToBox(Coord coord) { // метод закрытия ячейки
        flagMap.set(coord, Box.CLOSED); // по переданным координатам закрываем ячейку
        countOfFlags--; // ?? обратно уменьшаем количество флагов
    }

    private void setQuestionToBox(Coord coord) { // метод для вопросительного знака
        flagMap.set(coord, Box.INFORM); // по переданным координатам ставим вопрос

    }

    private void setFlagedToBox(Coord coord) { // метод для установки флажка
        flagMap.set(coord, Box.FLAGED); // по переданным координатам ставим флажок на ячейку
        countOfFlags++; // увеличиваем значение флажков на единицу
    }

    int getCountOfClosedBoxes() { // геттер количества закрытых ячеек
        return countOfClosedBoxes; // возвращаем количество закрытых ячеек
    }

    void setBombedToBox(Coord coord) { // устанавливаем Бомбед на ячейку
        flagMap.set(coord, Box.BOMBED); // по координате ставим Бомбед
    }

    void setOpenedToClosedBombBox(Coord coord) { // метод открытия ячеек с бомбами
        if (flagMap.get(coord) == Box.CLOSED) { // если ячейка закрыта
            flagMap.set(coord, Box.OPENED); // открываем ее
        }
    }

    void setFlagedToOpenedBombBox(Coord coord) { // метод открытия ячеек с бомбами (при выигрыше)
        if (flagMap.get(coord) == Box.FLAGED) { // если ячейка с флажком
            flagMap.set(coord, Box.OPENED); // открываем ее
        }
    }

    void setNobombToFlagedSafeBox(Coord coord) { // метод установки нобомб на ячейки с флажками
        if (flagMap.get(coord) == Box.FLAGED) { // если есть флажок
            flagMap.set(coord, Box.NOBOMB); // ставим нобомб
        }
    }

    int getCountOfFlagedBoxesAround(Coord coord) {
        int count = 00; // переменная количества флагов
        for (Coord around : Ranges.getCoordsAround(coord)) { // перебираем все клетки вокруг ячейки
            if (flagMap.get(around) == Box.FLAGED) { // если флаг есть
                count++; // увеличиваем количество на единицу
            }
        }
        return count; // возвращаем количество флагов
    }
}
package com.shifuu.aawws.mainactivity.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shifuu.aawws.databinding.FragmentUserBinding;
import com.shifuu.aawws.loginactivity.LoginActivity;
import com.shifuu.aawws.model.Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserFragment extends Fragment {


    private FragmentUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.d("UserFrag", uId);
        Query query = FirebaseFirestore.getInstance().collection("booking").whereEqualTo("uId", uId);
        FirestoreRecyclerOptions<Booking> options = new FirestoreRecyclerOptions.Builder<Booking>()
                .setQuery(query, new SnapshotParser<Booking>() {
                    @NonNull
                    @Override
                    public Booking parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        Booking b = new Booking();
                        b.setName(snapshot.get("name", String.class));
                        b.setQua(snapshot.get("qua", String.class));
                        b.setuId(snapshot.get("uId", String.class));
                        b.setItem(snapshot.get("item", String.class));
                        b.setBookId(snapshot.getId());
                        return b;
                    }
                }).setLifecycleOwner(getViewLifecycleOwner()).build();

        BookingsFirestoreAdapter adapter = new BookingsFirestoreAdapter(options);

        binding.fragUserRecycler.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        binding.fragUserRecycler.setAdapter(adapter);


    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        binding.fragUserBody.setText(user.getEmail());

        binding.fragUserButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                exitToLogout();
//                click();

            }
        });
    }

    private void exitToLogout() {
        Intent i = new Intent(getContext(), LoginActivity.class);
        this.startActivity(i);
        this.getActivity().finish();
    }



    private void click()
    {
//        put("штука тринога для костра", 1, "A", "1", "1.1");
//        put("штука тринога для костра", 1, "A", "1", "1.1");


        put("штука тринога для костра", 1, "A", "1", "1.1");
        put("пакеты подарочные ", 12, "A", "1", "1.2");
        put("карточки для воронки, прога и просто вороны и маленькие редбулы картинки", 100, "A", "1", "1.3");
        put("дискошары, коробки из касет и виниовые пластинки ", 13, "A", "1", "1.4");
        put("чемодан пустой серый", 1, "A", "2", "2.3");
        put("чемодан пустой красный ", 1, "A", "2", "2.4");
        put("белая коробка деревяная", 1, "A", "2", "2.6");
        put("мыло хозяйственное ", 20, "A", "2", "2.7");
        put("пластинки музыкальные", 47, "A", "2", "2.8");
        put("пластинки музыкальные", 50, "A", "2", "2.9");
        put("бумага для флипчартов", 1, "A", "3", "3.9");
        put("бумага для флипчартов", 1, "A", "3", "3.4");
        put("бумага для флипчартов", 1, "A", "3", "3.5");
        put("бумага для флипчартов", 1, "A", "3", "3.6");
        put("бумага для флипчартов", 1, "A", "3", "3.7");
        put("бумага для флипчартов", 1, "A", "3", "3.8");
        put("бумага для флипчартов", 1, "A", "3", "3.10");
        put("бумага крафтовая", 1, "A", "3", "3.11");
        put("два спальника", 2, "A", "3", "3.13");
        put("бумага для флипчартов", 3, "A", "3", "3.14");
        put("бумага для флипчартов", 6, "A", "3", "3.15");
        put("утеплитель для чилла", 1, "A", "4", "4.1");
        put("утеплитель ", 1, "A", "4", "4.2");
        put("роллап", 1, "A", "5", "5.1");
        put("роллап", 1, "A", "5", "5.2");
        put("роллап", 1, "A", "5", "5.3");
        put("роллап", 1, "A", "5", "5.4");
        put("роллап", 1, "A", "5", "5.5");
        put("роллап", 1, "A", "5", "5.6");
        put("держатель для роллапов", 1, "A", "5", "5.7");
        put("роллап", 1, "A", "5", "5.8");



        put("коробки", 100, "B", "1", "Без коробки");
        put("куб с картинками ", 1, "B", "1", "1.1");
        put("куб с картинками ", 1, "B", "1", "1.2");
        put("колышки отдельные (набор)", 2, "B", "2", "2.1");
        put("палатки", 6, "B", "2", "2.2");
        put("спальники ", 6, "B", "2", "2.3");
        put("коврики", 100, "B", "3", "3.1");
        put("утеплитель", 1, "B", "4", "4.1");
        put("утеплитель", 1, "B", "4", "4.2");
        put("большая цифра 1 нарисованные карандашики", 1, "B", "5", "5.1");




        put("пустые коробки", 100, "C", "1", "весь стеллаж");
        put("пустые коробки", 100, "C", "2", "весь стеллаж");
        put("пустые коробки", 100, "C", "3", "весь стеллаж");
        put("большие катушка", 1, "C", "4", "4.1");
        put("большая катушка ", 1, "C", "4", "4.2");


        put("Светомузыка", 3, "D", "1", "1.1");
        put("Крокодилы", 3, "D", "1", "1.2");
        put("Провода", 3, "D", "1", "1.3");
        put("Ящик Техдира", 3, "D", "1", "1.4");
        put("Болгарка, шлифовка, лобзик", 3, "D", "1", "1.5");
        put("Клей пестик и стержни", 3, "D", "2", "2.1");
        put("Перчатки", 3, "D", "2", "D.2.2");
        put("Фонарики", 3, "D", "2", "D.2.3");
        put("Гаечный ключ, паяльник, отвертка", 3, "D", "2", "D.2.4.");
        put("Лэд подсветка + пульты", 3, "D", "2", "D.2.5.");
        put("Моток цепи", 3, "D", "2", "D.2.6.");
        put("Батарейки", 3, "D", "3", "D.3. 1");
        put("Батарейки", 3, "D", "3", "D.3. 2");
        put("Степлер, рулетка, скрепки для степлера", 3, "D", "3", "D.3. 3");
        put("Мел", 3, "D", "3", "D.3. 4");
        put("Крепежи для пресс волла", 3, "D", "4", "D.4.1");
        put("Крепежи для пресс волла", 3, "D", "4", "D.4.2");
        put("Проектор + провода", 3, "D", "4", "D.4.3");
        put("Гирлянда", 3, "D", "4", "D.4.5");


        put("Генератор ", 1, "E", "1", "Без коробки");
        put("Электропила ", 3, "E", "1", "Без коробки");
        put("Пила", 6, "E", "1", "1.3");
        put("Катушка", 1, "E", "1", "1.2");
        put("Дыммашина", 1, "E", "1", "1.1");
        put("Коробка с проводами ", 1, "E", "1", "1.1");
        put("Бензин ", 3, "E", "1", "Без коробки");
        put("Чемодан с аккумуляторами", 1, "E", "1", "Без коробки");
        put("Леска ", 2, "E", "1", "1.3");
        put("Клей для камня ", 2, "E", "1", "Без коробки");
        put("Шуруповёрт ", 1, "E", "2", "Без коробки");
        put("Клеевой пистолет ", 2, "E", "2", "2.1");
        put("Инструменты ", 1, "E", "2", "2.2");
        put("Клей пистолеты+стержни ", 4, "E", "2", "Без коробки");
        put("Батарейки ", 1, "E", "2", "2.3");
        put("Очки для бассейна ", 3, "E", "2", "2.4");
        put("Мелки ", 4, "E", "2", "2.5");
        put("Фонари ", 8, "E", "2", "2.6");
        put("Перчатки ", 4, "E", "2", "2.7");
        put("Коробка с гвоздями ", 4, "E", "2", "2.8");
        put("Шурупы, гвозди и стяжки ", 4, "E", "2", "2.9");
        put("Крепежи ", 4, "E", "2", "2.10");
        put("Провода ", 4, "E", "3", "3.1");
        put("Скобы", 4, "E", "3", "3.2");
        put("Крокодилы ", 4, "E", "3", "3.3");
        put("Крепления, батарейки ", 4, "E", "3", "3.4");
        put("Цепи ", 4, "E", "3", "3.5");
        put("Пленка ", 4, "E", "4", "Без коробки");
        put("Лампы", 4, "E", "4", "Без коробки");
        put("Диодка ", 4, "E", "4", "Без коробки");
        put("Розжиг ", 4, "E", "4", "4.1");
        put("Гирлянды ", 4, "E", "4", "4.2");
        put("Фонари ", 4, "E", "4", "4.3");


        put("салфетки", 100, "F", "2", "2.1");
        put("салфетки", 100, "F", "2", "2.2");
        put("конверты hse run ", 200 , "F", "3", "3.1 - 3.13");
        put("бумажные пакеты ", 18, "F", "3", "3.14");
        put("белые пакеты ", 100 , "F", "3", "3.15 ");
        put("лототрон", 1, "F", "3", "3.16");
        put("крафтовая бумага", 110, "F", "3", "3.17");
        put("книги", 36, "F", "4", "4.1");
        put("пресс воллы ", 2, "F", "4", "4.2");
        put("желтые железные корзины ", 7, "F", "5", "5");
        put("рулон марли ", 110 , "F", "5", "5");





        put("одноразовая посуда", 100, "G", "3", "1.2");
        put("все для шашлыков ", 100, "G", "3", "1.3");
        put("рамки ", 25, "G", "2", "2.7");
        put("стаканы одноразовые белые ", 24 , "G", "2", "3.1");
        put("стакаканы одноразовые", 100, "G", "2", "3.2");
        put("стаканы одноразовые ", 10, "G", "2", "3.3");
        put("стаканы одноразовые ", 100 , "G", "2", "3.4");
        put("стаканы одноразовые ", 20, "G", "2", "3.5");
        put("стаканы одноразовые + крышки для стаканов", 3  , "G", "4", "4.1");
        put("чай", 4 , "G", "4", "4.2 - 4.5");
        put("посуда для готовки (кострюли, казаны, скороводки)", 5, "G", "1", "1.4");
        put("вода бутилированная (500 мл)", 15, "G", "2", "2.1");
        put("рамки + грамоты выпускников", 100, "G", "2", "2.2");
        put("ткани", 100, "G", "1", "1.6");
        put("пледы", 100 , "G", "1", "1.7");
        put("старый мангал", 1 , "G", "1", "1.8");
        put("потеряшки", 4, "G", "1", "1.9");





        put("ткань", 18, "H", "1", "1.1");
        put("дождевики", 18, "H", "2", "1.2");
        put("дождевики", 18, "H", "2", "2.3-2.5");
        put("Дождевики", 18, "H", "2", "2.6");
        put("Дождевики", 18, "H", "2", "2.8");
        put("мыльные пузыри", 18, "H", "2", "2.7");
        put("Обувь", 2, "H", "2", "2.10");
        put("Обувь", 2, "H", "2", "2.11");
        put("Шапки", 18, "H", "2", "2.12");
        put("кружки для леса ", 18, "H", "3", "3.9");
        put("Дискография + новогодние шары+рамки )", 18, "H", "3", "3.10");
        put("подушки", 18, "H", "3", "3.11");
        put("термокружки", 18, "H", "Без полки", "Без коробки");
        put("котелки", 18, "H", "Без полки", "Без коробки");
        put("вышалки ", 18, "H", "2", "12");
        put("тёплая одежда ", 18, "H", "1", "1.2");
        put("ткани поречье", 18, "H", "1", "1.3");
        put("шапки + одежда", 18, "H", "1", "1.5");
        put("пледы + одежда ", 18, "H", "1", "1.6");




        put("сетка", 1, "I", "2", "2.1");
        put("светофильтры и дождик и катушка ниток", 100, "I", "2", "2.2");
        put("кассеты с музыкой", 100, "I", "2", "2.3");
        put("мыльные пузыри", 100, "I", "2", "2.4");
        put("ламы и гирлянда НЕ УФ", 100, "I", "2", "2.5");
        put("бируши", 100, "I", "2", "2.6");
        put("арка старая загс", 1, "I", "2", "2.7");
        put("рамки оформление", 10, "I", "2", "2.8");
        put("лейки ", 19, "I", "2", "2.9");
        put("деревянная штука для сот", 1, "I", "2", "2.10");
        put("конусы", 8, "I", "2", "2.11");
        put("арка для загса новая", 1, "I", "2", "2.12");
        put("пакет с дисками ", 2, "I", "2", "2.13");
        put("нитка +лампочки уф", 9 , "I", "3", "3.1");
        put("виниловые пластинки", 30, "I", "3", "3.2");
        put("конусы", 4, "I", "3", "3.4");
        put("конусы", 6, "I", "3", "3.5");
        put("костюм монашки", 1, "I", "3", "3.6");
        put("дискошар и воздуховод", 1, "I", "3", "3.7");
        put("моток ваты и много бичевки", 100, "I", "3", "3.8");
        put("рамкидля картин", 4, "I", "3", "3.9");
        put("шары новогодние, кусты(листья), палки зеленые, рамки для фото и будильник ", 6, "I", "3", "3.10");
        put("велосипедное колесо", 11, "I", "3", "3.11");
        put("знак типо ауди", 7, "I", "3", "3.12");
        put("мячики нарисованные", 1, "I", "3", "3.13");
        put("кольца", 10, "I", "3", "3.14");
        put("рамки маленькие", 10, "I", "4", "4.1");
        put("круглая штука с дырками", 4, "I", "4", "4.2");
        put("веревка с колокольчиками", 9, "I", "4", "4.3");
        put("дошечки черные деревяные",  8, "I", "4", "4.4");
        put("таблички синие", 5, "I", "4", "4.5");
        put("ДС + черточка", 2, "I", "4", "4.6");
        put("черная рука дощечка", 1, "I", "4", "4.7");
        put("подставки для колб", 1, "I", "4", "4.8");
        put("бутылка стеклянная с отверствиями", 1, "I", "4", "4.9");
        put("подставка под бутылку", 1, "I", "5", "5.1");
        put("длинная лампа", 1, "I", "5", "5.2");
        put("черная ткань закрепленная на пробковую доску", 1, "I", "5", "5.3");
        put("розовые дощечки ", 123, "I", "5", "5.4");
        put("розовая большая доска", 2, "I", "5", "5.5");





        put("Коробка алкогеймс наследие (всё для будущих ответственных)", 1122121, "J", "1", "1.1");
        put("Маски для чилла", 38, "J", "1", "1.2");
        put("Прищепки", 10000, "J", "1", "1.2");
        put("Ведра", 6, "J", "1", "1.2");
        put("Ракетки для бадминтона", 3, "J", "1", "1.2");
        put("Старая дженга", 100, "J", "1", "1.2");
        put("Теннисные мячи в корзине", 100, "J", "1", "1.3");
        put("Шарики воздушные разные", 100, "J", "1", "1.4");
        put("Твистер", 1, "J", "1", "1.5");
        put("барабан", 1, "J", "1", "1.6");
        put("Светильник-тестер для лампочек", 1, "J", "1", "1.7");
        put("Шарики для сухого бассейна ", 8, "J", "2", "2.1");
        put("Корона ", 2, "J", "2", "2.2");
        put("Пистолеты зажигалки", 2, "J", "2", "2.2");
        put("Дротики", 5, "J", "2", "2.2");
        put("Шарики для бирпонга", 16, "J", "2", "2.3");
        put("Ленточки", 100, "J", "2", "2.4");
        put("Костюмы сумоистов", 2, "J", "2", "2.5");
        put("Пивополия", 8, "J", "2", "2.6");
        put("Флаги", 100, "J", "2", "2.7");
        put("Каска пожарного", 1, "J", "2", "2.8");
        put("маски для сна", 4, "J", "3", "3.1");
        put("буквы и цифры из дерева", 100, "J", "3", "3.2");
        put("мышеловки", 100, "J", "3", "3.3");
        put("бабломет", 1, "J", "3", "3.4");
        put("шприцы", 100, "J", "3", "3.5");
        put("костюм шута + каникалон + красный парик", 1, "J", "3", "3.6");
        put("микроскоп", 1, "J", "3", "3.7");
        put("мыльные пузыри", 100, "J", "3", "3.8");
        put("бутылочки детские и кислородные маски ПИВОПОЛИЯ", 100, "J", "3", "3.9");
        put("мантии пореченские с открытия(наверное)", 4, "J", "3", "3.10");
        put("водные пистолеты", 2, "J", "3", "3.11");
        put("полка с кучей стекла, рюмок, стопок, колбочек с 35-го прч", 5, "J", "4", "Без коробки");
        put("подушки", 6, "J", "5", "Без коробки");



        put("стул", 1, "K", "1", "К.1.1");
        put("подсвечник ", 1, "K", "1", "К.1.2");
        put("костюм дорожника и рубашки", 3, "K", "1", "К.1.3");
        put("клубки ниток, ленты, шар", 30, "K", "1", "К.1.4");
        put("деревянные ложки", 5, "K", "1", "К.1.5");
        put("желтый телефизор ", 1, "K", "2", "К.2.1");
        put("спирали", 2, "K", "2", "К.2.2");
        put("черный телевизор", 1, "K", "2", "К.2.3");
        put("черный большой телевизор", 1, "K", "2", "К.2.4");
        put("скалка ", 1, "K", "2", "К.2.5");
        put("дощечка ", 1, "K", "2", "К.2.6");
        put("серый телевизор", 1, "K", "2", "К.2.7");
        put("таблички с цифрами", 11, "K", "3", "К.3.1");
        put("таблички с цифрами", 13, "K", "3", "К.3.2");
        put("диски", 18, "K", "3", "К.3.3");
        put("виниловые пластинки ", 54, "K", "3", "К.3.4");
        put("табличка Duty Free", 1, "K", "3", "К.3.5");
        put("кассеты ", 9, "K", "3", "К.3.6");
        put("пустые карбоки из-под кассеты ", 30, "K", "3", "К.3.7");
        put("машины для попкорна и пакетики для попкорна ", 150, "K", "3", "К.3.8");
        put("доски ", 3, "K", "3", "К.3.9");
        put("зеркало разрисованное ", 1, "K", "4", "К.4.1");
        put("зеркало без рамки", 1, "K", "4", "К.4.2");
        put("доска с буквами ", 1, "K", "4", "К.4.3");
        put("доска с лампочками ", 1, "K", "4", "К.4.4");
        put("таблички  МузВышкой", 2, "K", "4", "К.4.5");
        put("дощечки с блестками ", 4, "K", "4", "К.4.6");
        put("дрон", 1, "K", "5", "К.5.1");
        put("подушка для лаввышки", 1, "K", "5", "К.5.2");
        put("подушка для лаввышки", 1, "K", "5", "К.5.3");



        put("музвышка", 1, "L", "4", "Без коробки");
        put("музвышка", 1, "L", "5", "Без коробки");





    }



    private synchronized void put(String name, int qua, String rack, String shelf, String box)
    {

        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("qua", qua);
        map.put("booked", 0);

        FirebaseFirestore.getInstance().collection("items").add(map).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if (task.isSuccessful())
                {
                    DocumentReference doc = task.getResult();

                    FirebaseFirestore.getInstance()
                            .collection("warehouse")
                            .document(rack)
                            .collection("shelf")
                            .document(shelf)
                            .collection("box")
                            .document(box)
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful())
                                    {
                                        DocumentSnapshot snapshot = task.getResult();

                                        List<DocumentReference> list = (List<DocumentReference>) snapshot.get("array");

                                        if (list == null)
                                            list = new ArrayList<>();

                                        list.add(doc);

//                                        list = new ArrayList<>();


                                        Map<String, Object> mapp = new HashMap<>();

                                        mapp.put("array", list);

                                        FirebaseFirestore.getInstance()
                                                .collection("warehouse")
                                                .document(rack)
                                                .collection("shelf")
                                                .document(shelf)
                                                .collection("box")
                                                .document(box).update(mapp);
                                    }
                                }
                            });
                }
            }
        });
    }





}







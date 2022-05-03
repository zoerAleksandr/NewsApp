# NewsApp
Автор - Александр Ящук

Дата создания проекта - 02.05.2022

Приложение создан для отработки практических навыков по Retrofit, RxJava3, Room, Koin, CleanArchitecture, WorkManager
Приложение реализует поиск новостей через newsapi.org по категориям, а также поиск по содержанию.

Задачи: 
 - Доделать WorkManager, для автозагрузки данных после отсутствия сети (реализовано ручное обновление по кнопке на SnackBar)
 - Исправить получение данных после подключения к сети, если ранее отображались данные из Room (ParentRecyclerViewAdapter)
 - Изменить место открытия SearchResultFragment (нужно чтобы появлялся под полем поиска)
 - Доделать fragment_search_result.xml
 - если возможно уменьшить разрешение изображений при загрузке для увалеичения скорости загрузки

Сделал:
 - Изменил место открытия SearchResultFragment (нужно чтобы появлялся под полем поиска) выполнено - ветка `fixed_fragment_layout_position`
 - Доделать fragment_search_result.xml выполнено - ветка `fix_fragment_search_result`
 
<img src = "./NewsAppGif.gif" alt="NewsAppGif" height="480">

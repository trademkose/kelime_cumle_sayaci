Aşağıdaki kriteleri sağlayan basit bir kelime ve cümle sayacı yapmak istersek;
Parametre olarak bir txt dosyasının dosya sistemindeki adresini alacak.
Programda bir ana thread ve sayısı parametrik olan (default 5) birden çok yardımcı thread olacak.
Ana thread başladığında thread sayısı parametresinin olduğu kadar thread başlatacak. Verilen dosyayı okuyup
cümlelerine ayıracak, cümle sayısını ve tüm cümlelerdeki ortalama kelime sayısını tutacak.
Ana thread metin içindeki her cümleyi sırayla başlattığı threadlerin bir tanesinin kuyruğuna ekleyecek, her
cumle icin ayri thread başlatılmayacak.
Her yardımcı thread, kuyruğuna eklenen cümleleri kelimelerine ayıracak ve her kelime için global tutulan bir
listede, ilgili kelime sayısını güncelleyecek.
Ana thread tüm yardımcı threadlerin işlerini bitirmesini bekleyecek, cümle sayısını, ortalama kelime sayısını,
her thread'in işlediği cümle sayısını ve tüm threadlerin oluşturdugu listeyi sayı ve kelimeler olarak ekrana
yazdıracak.
Kelimeler büyük-küçük harf duyarlı tutulacak ve sayıca azalan sırada yazdırılacak. Örnek bir çalışma için bir
paragraf ve beklenen çıktıyı aşağıda bulabilirsiniz.


Was certainty remaining engrossed applauded now sir how discovery. Settled opinion how enjoyed greater joy
adapted too shy? Now properly surprise expenses interest nor replying she she. Bore sir tall nay many many
time yet less.

Sentence Count : 4
Avg. Word Count : 9
Thread counts:
ThreadId=4, Count=1
ThreadId=5, Count=1
ThreadId=7, Count=1
ThreadId=8, Count=0
ThreadId=3, Count=1
sir 2
how 2
she 2
many 2
Was 1
certainty 1
remaining 1
engrossed 1
applauded
discovery 1
Settled 1
opinion 1
enjoyed 1
greater 1

joy 1
adapted 1
too 1
shy 1
Now 1
properly 1
surprise 1
now 1
expenses 1
interest 1
nor 1
replying 1
Bore 1
tall 1
nay 1
time 1
yet 1
less 1

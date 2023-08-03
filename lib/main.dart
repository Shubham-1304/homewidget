
import 'dart:io';
import 'dart:math';
import 'dart:ui';
import 'dart:developer';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:home_widget/home_widget.dart';
import 'package:fluttertoast/fluttertoast.dart';
// import 'package:shared_preferences/shared_preferences.dart';

// const MethodChannel _channel = MethodChannel('com.example.app/myMethodChannel');
//
// void myDartMethod(String data) {
//   // Your logic here
//   print('Received data from Kotlin: $data');
// }


void main() {
  WidgetsFlutterBinding.ensureInitialized();
  // HomeWidget.registerBackgroundCallback(selectCallback);
  print("working");
  initializeAndroidWidgets();
  // HomeWidget.renderFlutterWidget(Text("Hello"), key: "dashIcon");
  runApp(const MyApp());


}

void initializeAndroidWidgets() {
  if (Platform.isAndroid) {
    // Intialize flutter
    WidgetsFlutterBinding.ensureInitialized();

    const MethodChannel channel = MethodChannel('com.example.app/widget');

    final CallbackHandle? callback = PluginUtilities.getCallbackHandle(onWidgetUpdate);
    print("callback $callback");
    final handle = callback!.toRawHandle();
    print("working till here $handle");

    channel.invokeMethod('initialize', handle).then((value) => print("value $value"));
  }
}

void onWidgetUpdate() {
  // Intialize flutter
  WidgetsFlutterBinding.ensureInitialized();

  const MethodChannel channel = MethodChannel('com.example.app/widget');
  print("call back handle called");


  // If you use dependency injection you will need to inject
  // your objects before using them.

  channel.setMethodCallHandler(
        (call) async {
          if (call.method=="update"){
            print("update here");

          }
          if (call.method=="received"){
            print("received channel");
          }
      final id = call.arguments;

      print('on Dart ${call.method}!');

      // Do your stuff here...
      final result = Random().nextDouble();

      return {
        // Pass back the id of the widget so we can
        // update it later
        'id': id,
        // Some data
        'value': result,
      };
    },
  );
}


dynamic selectCallback(Uri? uri) async {
  if (uri!.host == 'selecttile') {
    print("uri host selectChip called");
    String _selected = "";
    await HomeWidget.getWidgetData<String>('_selected', defaultValue: "")
        .then((value) {
      print("selectCallback working $value");
      Fluttertoast.showToast(
          msg: "registerBackgroundCallback called",
          toastLength: Toast.LENGTH_SHORT,
          gravity: ToastGravity.CENTER,
          timeInSecForIosWeb: 1,
          backgroundColor: Colors.red,
          textColor: Colors.white,
          fontSize: 16.0
      );
    });
    await HomeWidget.saveWidgetData<String>('_selected', _selected);
    await HomeWidget.updateWidget(
        name: 'StackWidgetProvider', iOSName: 'StackWidgetProvider');
  }
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  String _selected = '';
  int selectedIndex=-1;
  static const platform = MethodChannel('com.example.myChannel');
  // static const homeChannel=MethodChannel('com.example.imaginego_homewidget/home');
  // static const eventChannel=EventChannel("com.example.imaginego_homewidget/event");

  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    loadData();
    // onListenData();
    // onStreamEvent();
  }

  // void onStreamEvent(){
  //   eventChannel.receiveBroadcastStream().listen((event) {
  //     print(event);
  //   });
  // }

  // void onListenData(){
  //   homeChannel.setMethodCallHandler((call) async{
  //     if(call.method=="getIndex"){
  //       selectedIndex=call.arguments;
  //       print("selectedIndex $selectedIndex");
  //       setState(() {

  //       });
  //     }
  //   });
  // }

  void loadData() async {
    await HomeWidget.saveWidgetData<String>("_selected", "").then((value) => print("saved $value"));
    await HomeWidget.getWidgetData<String>('_selected', defaultValue: "nothing")
    // await HomeWidget.getWidgetData<int>('_counter', defaultValue: 0)
        .then((value) {
        print(value);
      _selected = value!;
    });
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    platform.setMethodCallHandler((call) async {
      if (call.method == "myMethodFromAndroid") {
        String dataFromAppWidgetProvider = call.arguments;
        _myDartFunction(dataFromAppWidgetProvider);
      }
    });
    // This method is rerun every time setState is called, for instance as done
    // by the _incrementCounter method above.
    //
    // The Flutter framework has been optimized to make rerunning build methods
    // fast, so that you can just rebuild anything that needs updating rather
    // than having to individually change instances of widgets.
    return Scaffold(
      appBar: AppBar(
        // TRY THIS: Try changing the color here to a specific color (to
        // Colors.amber, perhaps?) and trigger a hot reload to see the AppBar
        // change color while the other colors stay the same.
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        // Here we take the value from the MyHomePage object that was created by
        // the App.build method, and use it to set our appbar title.
        title: Text(widget.title),
      ),
      body: Center(
        // Center is a layout widget. It takes a single child and positions it
        // in the middle of the parent.
        child: Column(
          // Column is also a layout widget. It takes a list of children and
          // arranges them vertically. By default, it sizes itself to fit its
          // children horizontally, and tries to be as tall as its parent.
          //
          // Column has various properties to control how it sizes itself and
          // how it positions its children. Here we use mainAxisAlignment to
          // center the children vertically; the main axis here is the vertical
          // axis because Columns are vertical (the cross axis would be
          // horizontal).
          //
          // TRY THIS: Invoke "debug painting" (choose the "Toggle Debug Paint"
          // action in the IDE, or press "p" in the console), to see the
          // wireframe for each widget.
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              _selected,
              style: Theme.of(context).textTheme.headlineMedium,
            ),
          ],
        ),
      ),
      // floatingActionButton: FloatingActionButton(
      //   onPressed: _incrementCounter,
      //   tooltip: 'Increment',
      //   child: const Icon(Icons.add),
      // ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
  void _myDartFunction(String data) {
    print('Data received in Flutter: $data');
    // Perform any desired actions with the data here
    // ...
  }
}

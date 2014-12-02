/* global Firebase */
'use strict';
/* App Controllers */


var memoryGameApp = angular.module('memoryGameApp', []);


memoryGameApp.factory('game', function() {
  var factory = {}
  var tileNames = ['8-ball', 'kronos', 'baked-potato', 'dinosaur', 'rocket', 'skinny-unicorn',
    'that-guy', 'zeppelin','8-ball', 'kronos', 'baked-potato', 'dinosaur', 'rocket', 'skinny-unicorn',
    'that-guy', 'zeppelin'];

  return new Game(tileNames);
});


memoryGameApp.controller('GameCtrl', function GameCtrl($scope, game,$timeout) {
  $scope.game = game;
 // console.log($scope.game.tileDeck);
   $scope.gamePlay = [];
 // console.log($scope.game.tileDeck);
  var rootRef = new Firebase('https://vivid-heat-5137.firebaseio.com/')
  var messagesRef = rootRef.child('messages');

  messagesRef.on('child_added',function(snapshot) {
    $timeout(function() {
      var snapshotVal = snapshot.val();
      console.log(snapshotVal.card);
       $scope.gamePlay.push(snapshotVal.agent + " says open" + snapshotVal.card)
      $scope.game.flipTile($scope.game.tileDeck[snapshotVal.card],snapshotVal.agent);
      $timeout(function() {
        console.log("test");
        $scope.game.flipTile(null,null);
          console.log("completed");
      },2000);
     // $scope.messages = snapshotVal;
      //$scope.game.flipTile(snapshotVal.card);
    });

  } );


  $scope.sendMessage = function() {
      var newMessage = {   
      card : $scope.game.val,
      agent : 'Bob'
      };
      messagesRef.push(newMessage);
  };
});


//usages: asgfasgfasf
//- in the repeater as: <mg-card tile="tile"></mg-card>
//- card currently being matched as: <mg-card tile="game.firstPick"></mg-card>

memoryGameApp.directive('mgCard', function() {
  return {
    restrict: 'E',
    // instead of inlining the template string here, one could use templateUrl: 'mg-card.html'
    // and then either create a mg-card.html file with the content or add
    // <script type="text/ng-template" id="mg-card.html">.. template here.. </script> element to
    // index.html
    template: '<div class="container">' +
                '<div class="card" ng-class="{flipped: tile.flipped}">' +
                  '<img class="front" ng-src="img/back.png">' +
                  '<img class="back" ng-src="img/{{tile.title}}.png">' +
                '</div>' +
              '</div>',
    scope: {
      tile: '='
    }
  }
});

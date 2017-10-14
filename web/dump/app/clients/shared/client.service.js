/**
 * Created by tudor on 4/13/2017.
 */
"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
var core_1 = require('@angular/core');
var http_1 = require("@angular/http");
var rxjs_1 = require("rxjs");
require('rxjs/add/operator/catch');
require('rxjs/add/operator/map');
var ClientService = (function () {
    function ClientService(http) {
        this.http = http;
        this.clientsUrl = 'http://localhost:8080/api/clients';
    }
    ClientService.prototype.getClients = function () {
        return this.http.get(this.clientsUrl)
            .map(this.extractData)
            .catch(this.handleError);
    };
    ClientService.prototype.extractData = function (res) {
        var body = res.json();
        return body.clients || {};
    };
    ClientService.prototype.handleError = function (error) {
        var errMsg;
        if (error instanceof http_1.Response) {
            var body = error.json() || '';
            var err = body.error || JSON.stringify(body);
            errMsg = error.status + " - " + (error.statusText || '') + " " + err;
        }
        else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return rxjs_1.Observable.throw(errMsg);
    };
    ClientService.prototype.getClient = function (id) {
        return this.getClients()
            .map(function (clients) { return clients.find(function (client) { return client.id === id; }); });
    };
    ClientService = __decorate([
        core_1.Injectable()
    ], ClientService);
    return ClientService;
}());
exports.ClientService = ClientService;

import {ApplicationRef, Injectable} from "@angular/core";

import {config} from "../smartadmin.config";
import {languages} from "./languages.model";
import {JsonApiService} from "../../core/api/json-api.service";
import {Subject} from "rxjs/Subject";


@Injectable()
export class I18nService {

    public state;
    public static data;
    public currentLanguage: any;

    public static locale = config.defaultLocale;


    constructor(private jsonApiService: JsonApiService, private ref: ApplicationRef) {
        if (!I18nService.data) { //fetch language roi thi khong fetch lai nua
            I18nService.data = {};
            this.state = new Subject();
            // this.initLanguage(config.defaultLocale || 'us');
            this.initLanguage(I18nService.locale || 'us');
            this.fetch(this.currentLanguage.key);
        }
    }

    private fetch(locale: any) {
        // this.jsonApiService.fetch(`/langs/${locale}.json`)
        //     .subscribe((data: any) => {
        //         I18nService.data = data;
        //         console.log('data: ' + JSON.stringify(data));
        //         this.state.next(I18nService.data);
        //         this.ref.tick();
        //     })

        this.jsonApiService.fetchFromServer(locale)
            .subscribe((data: any) => {
                I18nService.data = data;
                this.state.next(I18nService.data);
                this.ref.tick();
            });
    }

    public saveOrUpdateByKey(key, value) {
        if (I18nService.data && I18nService.data instanceof Array) {
            var trans = I18nService.data.find((content) => {
                return content.key == key;
            });
            if (trans) {
                trans.value = value;
            } else {
                let lan = {id: 0, key: key, value: value, locale: I18nService.locale, status: 1};
                I18nService.data.push(lan);
                console.log('add language content, key: ' + key);
            }
        }
    }

    public saveOrUpdateByListKey(listLanguage: Array<any>) {
        for (let language of listLanguage) {
            this.saveOrUpdateByKey(language.key, language.value);
        }
    }

    private initLanguage(locale: string) {
        let language = languages.find((it) => {
            return it.key == locale;
        });
        if (language) {
            this.currentLanguage = language;
        } else {
            throw new Error(`Incorrect locale used for I18nService: ${locale}`);
        }
    }

    setLanguage(language) {
        I18nService.locale = language.key;
        this.currentLanguage = language;
        this.fetch(language.key);
    }


    subscribe(sub: any, err: any) {
        return this.state.subscribe(sub, err);
    }

    byKey(phrase: string): string {
        if (I18nService.data && I18nService.data instanceof Array) {
            let trans = I18nService.data.find((content) => {
                return content.key == phrase;
            });
            return trans ? trans.value : phrase;
        }
        return null;
    }

    public getTranslation(phrase: string): string {
        // return I18nService.data && I18nService.data[phrase] ? I18nService.data[phrase] : phrase;
        return this.byKey(phrase) ? this.byKey(phrase) : phrase;
    }

    public getLocaleCurrent(): string {
        return I18nService.locale;
    }

}

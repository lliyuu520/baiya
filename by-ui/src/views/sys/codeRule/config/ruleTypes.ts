export interface RuleDetail {
    id: number
    sourceField: string
    weight: number
    indexBegin: number
    indexEnd: number
    encodeType: string
    constant: string,
    length: number,
    randomType: string,
    offsetYears: number
   
}

export interface SourceFieldOption {
    code: string
    name: string
}

export interface EncodeTypeOption {
    code: string
    name: string
}

export interface RandomTypeOption {
    code: string
    name: string
}

export const encodeTypeList: EncodeTypeOption[] = [
    { code: 'BASE_10', name: 'BASE_10' },
    { code: 'BASE_36', name: 'BASE_36' },
    { code: 'BASE_62', name: 'BASE_62' }
]

export const boxCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'FINISHED_PRODUCTION_DATE', name: '成品生产日期' },
    { code: 'FINISHED_DEPART_CODE', name: '成品车间编码' },
    { code: 'FINISHED_WORKSHOP_CODE', name: '成品产线编码' },
    { code: 'FINISHED_TEAM_CODE', name: '成品班次编码' },
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'RANDOM_STRING', name: '随机字符串' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'CONSTANT', name: '常量' }

]

export const innerBoxCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'FINISHED_PRODUCTION_DATE', name: '成品生产日期' },
    { code: 'FINISHED_DEPART_CODE', name: '成品车间编码' },
    { code: 'FINISHED_WORKSHOP_CODE', name: '成品产线编码' },
    { code: 'FINISHED_TEAM_CODE', name: '成品班次编码' },
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'RANDOM_STRING', name: '随机字符串' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'CONSTANT', name: '常量' }

]

export const bagCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'SEMI_FINISHED_PRODUCTION_DATE', name: '半成品生产日期' },
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'SEMI_FINISHED_DEPART_CODE', name: '半成品车间编码' },
    { code: 'SEMI_FINISHED_WORKSHOP_CODE', name: '半成品产线编码' },
    { code: 'SEMI_FINISHED_TEAM_CODE', name: '半成品班次编码' },
    { code: 'SEMI_FINISHED_ORDER_CODE', name: '半成品订单编码' },
    { code: 'SEMI_FINISHED_PRODUCT_CODE', name: '半成品产品编码' },
    { code: 'RANDOM_STRING', name: '随机字符串' },
    { code: 'BOX_NO', name: '箱号' },

]

export const universalCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'SEMI_FINISHED_PRODUCTION_DATE', name: '半成品生产日期' },
    { code: 'SEMI_FINISHED_DEPART_CODE', name: '半成品车间编码' },
    { code: 'SEMI_FINISHED_WORKSHOP_CODE', name: '半成品产线编码' },
    { code: 'SEMI_FINISHED_TEAM_CODE', name: '半成品班次编码' },
    { code: 'SEMI_FINISHED_ORDER_CODE', name: '半成品订单编码' },
    { code: 'SEMI_FINISHED_PRODUCT_CODE', name: '半成品产品编码' },
    { code: 'RANDOM_STRING', name: '随机字符串' },  
    { code: 'SPECIFY_BOX_NO', name: '指定箱号' },
]



export const randomTypeList: RandomTypeOption[] = [
    { code: 'NUMBER', name: '数字' },
    { code: 'UPPER_CASE_LETTER', name: '大写字母' },
    { code: 'LOWER_CASE_LETTER', name: '小写字母' },
    { code: 'NUMBER_UPPER_CASE_LETTER', name: '数字+大写字母' },
    { code: 'NUMBER_LOWER_CASE_LETTER', name: '数字+小写字母' },
    { code: 'NUMBER_UPPER_CASE_LETTER_LOWER_CASE_LETTER', name: '数字+大写字母+小写字母' }
]


export interface RuleDetail {
    id: number
    sourceField: string
    weight: number
    indexBegin: number
    indexEnd: number
    encodeType: string
    constant: string
}

export interface SourceFieldOption {
    code: string
    name: string
}

export interface EncodeTypeOption {
    code: string
    name: string
}

export const encodeTypeList: EncodeTypeOption[] = [
    { code: 'BASE_10', name: 'BASE_10' },
    { code: 'BASE_32', name: 'BASE_32' },
    { code: 'BASE_62', name: 'BASE_62' }
]

export const boxCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'FINISHED_LIMITED_USE_DATE', name: '成品限用日期' },
    { code: 'FINISHED_DEPART_CODE', name: '成品部门编码' },
    { code: 'FINISHED_WORKSHOP_CODE', name: '成品车间编码' },
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'CONSTANT', name: '常量' }

]

export const bagCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'SEMI_FINISHED_LIMITED_USE_DATE', name: '半成品限用日期' },
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'SEMI_FINISHED_DEPART_CODE', name: '半成品部门编码' },
    { code: 'SEMI_FINISHED_WORKSHOP_CODE', name: '半成品车间编码' },
    { code: 'SEMI_FINISHED_ORDER_CODE', name: '半成品订单编码' },
    { code: 'SEMI_FINISHED_PRODUCT_CODE', name: '半成品产品编码' },
    { code: 'BOX_NO', name: '箱号' },

]

export const universalCodeSourceFieldList: SourceFieldOption[] = [
    { code: 'FINISHED_ORDER_CODE', name: '成品订单编码' },
    { code: 'FINISHED_PRODUCT_CODE', name: '成品产品编码' },
    { code: 'SEMI_FINISHED_LIMITED_USE_DATE', name: '半成品限用日期' },
    { code: 'SEMI_FINISHED_DEPART_CODE', name: '半成品部门编码' },
    { code: 'SEMI_FINISHED_WORKSHOP_CODE', name: '半成品车间编码' },
    { code: 'SEMI_FINISHED_ORDER_CODE', name: '半成品订单编码' },
    { code: 'SEMI_FINISHED_PRODUCT_CODE', name: '半成品产品编码' },
    { code: 'SPECIFY_BOX_NO', name: '指定箱号' },
]


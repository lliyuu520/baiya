export interface RuleDetail {
    id: number
    sourceType: string
    sourceField: string
    weight: number
    indexBegin: number
    indexEnd: number
    encodeType: string
    constant: string
}

export interface SourceOption {
    code: string
    name: string
}

export interface FieldOption {
    code: string
    name: string
}

export const encodeType: SourceOption[] = [
    { code: 'base10', name: '10进制' },
    { code: 'base36', name: '36进制' },
    { code: 'base62', name: '62进制' }
]



export const boxCodeSourceList: SourceOption[] = [
    { code: 'FACTORY', name: '工厂' },
    { code: 'PRODUCTION_LINE', name: '生产线' },
    { code: 'TEAM', name: '班组' },
    { code: 'ORDER', name: '订单' },
    { code: 'PRODUCT', name: '产品' },
    { code: 'TIMESTAMP', name: '时间戳' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'SALT', name: '加密数' },
    { code: 'CONSTANT', name: '常量' }
]

export const bagCodeSourceList: SourceOption[] = [
    { code: 'FACTORY', name: '工厂' },
    { code: 'PRODUCTION_LINE', name: '生产线' },
    { code: 'TEAM', name: '班组' },
    { code: 'ORDER', name: '订单' },
    { code: 'PRODUCT', name: '产品' },
    { code: 'TIMESTAMP', name: '时间戳' },
    { code: 'SALT', name: '加密数' },
    { code: 'BOX_NO', name: '箱号' },
    { code: 'CONSTANT', name: '常量' }
]

export const universalCodeSourceList: SourceOption[] = [
    { code: 'FACTORY', name: '工厂' },
    { code: 'PRODUCTION_LINE', name: '生产线' },
    { code: 'TEAM', name: '班组' },
    { code: 'ORDER', name: '订单' },
    { code: 'PRODUCT', name: '产品' },
    { code: 'TIMESTAMP', name: '时间戳' },
    { code: 'RANDOM', name: '随机数' },
]

export const fieldOptions: Record<string, { code: string; name: string }[]> = {
    CONSTANT: [
        { code: 'INPUT', name: '输入' }
    ],
    FACTORY: [{ code: 'FACTORY_CODE', name: '工厂编码' }],
    PRODUCT: [{ code: 'PRODUCT_CODE', name: '产品编码' }],
    PRODUCTION_LINE: [{ code: 'PRODUCTION_LINE_CODE', name: '产线编码' }],
    TEAM: [{ code: 'TEAM_CODE', name: '班组编码' }],
    ORDER: [
        { code: 'ORDER_CODE', name: '订单编码' },
        { code: 'ORDER_DATE', name: '订单日期' }
    ],
    TIMESTAMP: [{ code: 'TIMESTAMP', name: '系统时间' }],
    BAG_NO: [{ code: 'SYSTEM', name: '系统生成' }],
    BOX_NO: [{ code: 'SYSTEM', name: '系统生成' }],
    RANDOM: [{ code: 'SYSTEM', name: '系统生成' }],
    SALT: [{ code: 'INPUT', name: '输入' }]
}
